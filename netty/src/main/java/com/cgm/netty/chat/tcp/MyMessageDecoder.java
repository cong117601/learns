package com.cgm.netty.chat.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        int length = in.readInt();
        byte[] context = new byte[length];
        in.readBytes(context);
        MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
        myMessageProtocol.setMessage(context);
        myMessageProtocol.setLen(length);
        //传递给下一个handler 使用
        list.add(myMessageProtocol);

    }
}
