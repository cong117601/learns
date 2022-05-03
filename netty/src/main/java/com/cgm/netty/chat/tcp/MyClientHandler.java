package com.cgm.netty.chat.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 200; i++) {
            String msg = "你好，我是cgm!";
            MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
            myMessageProtocol.setMessage(msg.getBytes(StandardCharsets.UTF_8));
            myMessageProtocol.setLen(msg.getBytes(StandardCharsets.UTF_8).length);
            ctx.writeAndFlush(myMessageProtocol);
            ctx.writeAndFlush(msg);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageProtocol myMessageProtocol) throws Exception {

        String s = new String(myMessageProtocol.getMessage(), CharsetUtil.UTF_8);
        System.out.println("client msg" + s);
        System.out.println("client len" + myMessageProtocol.getLen());
    }
}
