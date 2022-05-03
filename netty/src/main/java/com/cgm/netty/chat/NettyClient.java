package com.cgm.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备ok....");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //得到 channel
            Channel channel = channelFuture.channel();
            //客户端需要输入信息， 创建一个扫描器
//            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNextLine()) {
//                String msg = scanner.nextLine();
//                //通过 channel 发送到服务器端
//                channel.writeAndFlush(msg);
//            }
            //模拟粘包 拆包问题
            for (int i = 0; i < 20; i++) {
                channel.writeAndFlush("hello，CGM!" + "_");
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }


    }
}
