package com.cgm.netty.chat.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

//public class MyServerHandler extends SimpleChannelInboundHandler<String> {

public class MyServerHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] message = msg.getMessage();

        System.out.println("服务端接收到内容：" + "len: " + len + " message: " + new String(message, CharsetUtil.UTF_8));
        System.out.println("服务端接收到的包数量：" + (++this.count));


        //回复
        String str = "服务端,收到消息!";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
        myMessageProtocol.setMessage(bytes);
        myMessageProtocol.setLen(bytes.length);
        ctx.writeAndFlush(myMessageProtocol);
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//
//
//        System.out.println(msg);
//        System.out.println("服务端接收到的包数量：" + (++this.count));
//        //回复
//        String str = "服务端,收到消息!";
//        ctx.writeAndFlush(str);
//    }
}
