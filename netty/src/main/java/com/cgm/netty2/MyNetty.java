package com.cgm.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;


public class MyNetty {

    @Test
    public void loopExecutor() throws IOException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        eventExecutors.execute(() -> {
            for (; ; ) {
                try {
                    System.out.println("hello world");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        System.in.read();
    }

    @Test
    public void clientMode() throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(1);
        NioSocketChannel client = new NioSocketChannel();
        eventExecutors.register(client);
        //处理服务端发来的数据
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new MyInHandler());
        ChannelFuture sync = client.connect(new InetSocketAddress("192.168.139.141", 9000));
        sync.sync();
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello".getBytes());
        ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
        channelFuture.sync();

        sync.channel().closeFuture().sync();
        System.out.println("client close");
    }


    @Test
    public void serverMode() throws InterruptedException {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();

        thread.register(server);

        ChannelPipeline pipeline = server.pipeline();
        pipeline.addLast(new MyAcceptHandler(thread, new MyInHandler()));//接收客户端 并注册事件到selector

        ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 8888));
        bind.sync().channel().closeFuture().sync();
        System.out.println("server close...");

    }
}
@ChannelHandler.Sharable
class MyInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client register...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //接收数据
        ByteBuf buffer = (ByteBuf) msg;
        CharSequence str = buffer.getCharSequence(0, buffer.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        //回复数据
        ctx.writeAndFlush(buffer);

    }
}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server register...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel client = (SocketChannel) msg;
        //1.注册
        selector.register(client);
        //2.client的hanler
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(handler);

    }

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler channelHandler) {
        this.selector = thread;
        this.handler = channelHandler;

    }
}
