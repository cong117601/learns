package com.cgm.rpc2;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;
import springfox.documentation.RequestHandler;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class MyRpc {


    @Test
    public void get() {

        new Thread(()->{
            startServer();
        }).start();
        System.out.println("server start....");

        Car car = proxyGet(CarImpl.class);
        car.run("hello");
    }


    public static <T> T proxyGet(Class<T> interfaceInfo) {

        ClassLoader classLoader = interfaceInfo.getClassLoader();


        return (T) Proxy.newProxyInstance(classLoader, interfaceInfo.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String interfaceName = interfaceInfo.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                MyContent myContent = new MyContent(interfaceName, methodName, parameterTypes, args);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(myContent);
                byte[] msgBody = bos.toByteArray();

                MyHeader header = createHeader(msgBody);

                bos.reset();

                oos = new ObjectOutputStream(bos);
                oos.writeObject(header);
                byte[] msgHeader = bos.toByteArray();



                ClientFactory clientFactory = ClientFactory.getClientFactory();
                NioSocketChannel client = clientFactory.getClient(new InetSocketAddress("127.0.0.1", 9000));

                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);


                CountDownLatch countDownLatch = new CountDownLatch(1);

                long requestId = header.requestId;
                ResponseHandler.addCallBack(requestId, () -> {
                    countDownLatch.countDown();
                });

                byteBuf.writeBytes(msgHeader);
                byteBuf.writeBytes(msgBody);
                ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
                channelFuture.sync();


                countDownLatch.await();

                return null;
            }
        });


    }


    public void startServer() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = boss;
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture bind = bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server appcet port:"+ch.remoteAddress().getPort());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerRequestHandler());
                    }
                }).bind(new InetSocketAddress("127.0.0.1", 9000));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MyHeader createHeader(byte[] msg) {
        return new MyHeader(0x14141414, Math.abs(UUID.randomUUID().getLeastSignificantBits()), msg.length);

    }


}

class ServerRequestHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        ByteBuf copy = byteBuf.copy();

        System.out.println(byteBuf.readableBytes());
        if (byteBuf.readableBytes() >= 160) {
            byte[] bytes = new byte[160];
            byteBuf.readBytes(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oIn = new ObjectInputStream(in);
            MyHeader o = (MyHeader) oIn.readObject();
            System.out.println(o.requestId+"...server");
            if (byteBuf.readableBytes() >= o.dateLen) {
                byte[] data = new byte[(int) o.dateLen];
                byteBuf.readBytes(data);
                ByteArrayInputStream in2 = new ByteArrayInputStream(data);
                ObjectInputStream oIn2 = new ObjectInputStream(in2);
                MyContent o2 = (MyContent) oIn2.readObject();
                System.out.println(o2.methodName+"...server");

            }
        }
        ChannelFuture channelFuture = ctx.writeAndFlush(copy);

        channelFuture.sync();

    }
}

class ResponseHandler {

    static ConcurrentHashMap<Long, Runnable> concurrentHashMap = new ConcurrentHashMap<>();

    public static void addCallBack(long requestId, Runnable cb) {

        concurrentHashMap.putIfAbsent(requestId, cb);
    }

    public static void runCallBack(long requestId) {

        Runnable runnable = concurrentHashMap.get(requestId);
        runnable.run();
        concurrentHashMap.remove(requestId);
    }

}


class ClientFactory {

    int pollSize = 1;

    Random rad = new Random();
    private static ClientFactory clientFactory = new ClientFactory();

    private ClientFactory() {

    }

    public static ClientFactory getClientFactory() {
        return clientFactory;
    }

    ConcurrentHashMap<InetSocketAddress, ClientPool> map = new ConcurrentHashMap<>();

    public NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool clientPool = map.get(address);
        if (clientPool == null) {
            clientPool = new ClientPool(pollSize);
            map.putIfAbsent(address, clientPool);

        }
        int i = rad.nextInt(pollSize);
        NioSocketChannel client = clientPool.clients[i];
        if (client != null && client.isActive()) {
            return client;
        }
        synchronized (clientPool.lock[i]) {
            return clientPool.clients[i] = create(address);
        }
    }

    public static NioSocketChannel create(InetSocketAddress address) {


        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ClientResponse());
                    }
                }).connect(address);
        NioSocketChannel channel = null;
        try {
            channel = (NioSocketChannel) connect.sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channel;

    }

}


class ClientResponse extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;


        if (byteBuf.readableBytes() >= 399) {
            byte[] bytes = new byte[399];
            byteBuf.readBytes(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oIn = new ObjectInputStream(in);
            MyHeader o = (MyHeader) oIn.readObject();
            System.out.println(o.requestId+"  client");
            ResponseHandler.runCallBack(o.requestId);

//            if (byteBuf.readableBytes() >= o.dateLen) {
//                byte[] data = new byte[(int) o.dateLen];
//                byteBuf.readBytes(data);
//                ByteArrayInputStream in2 = new ByteArrayInputStream(data);
//                ObjectInputStream oIn2 = new ObjectInputStream(in2);
//                MyContent o2 = (MyContent) oIn2.readObject();
//            }
        }


    }
}

class ClientPool {

    NioSocketChannel[] clients;
    Object[] lock;

    ClientPool(int size) {
        clients = new NioSocketChannel[size];
        lock = new Object[size];

        for (int i = 0; i < lock.length; i++) {
            lock[i] = new Object();
        }

    }

}


class MyHeader implements Serializable {

    int flag;
    long requestId;
    long dateLen;

    public MyHeader(int flag, long requestId, long dateLen) {
        this.flag = flag;
        this.requestId = requestId;
        this.dateLen = dateLen;
    }
}


class MyContent implements Serializable {
    String interFaceName;
    String methodName;
    Class<?>[] argsType;
    Object[] args;

    public MyContent(String interFaceName, String methodName, Class[] argsType, Object[] args) {
        this.interFaceName = interFaceName;
        this.methodName = methodName;
        this.argsType = argsType;
        this.args = args;
    }
}

interface Car {

    void run(String msg);
}

class CarImpl implements Car {
    @Override
    public void run(String msg) {
        System.out.println(msg);
    }
}
