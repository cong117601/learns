package com.cgm.netty.protoBuf2;

import com.cgm.netty.protoBuf.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = 1;
        MyDataInfo.MyMessage message = null;
        if (0 == random) {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StrudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(222).setName("小王").build()).build();
        } else {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.TeacherType)
                    .setTeacher(MyDataInfo.Teacher.newBuilder().setAge(222).setTName("仓老司机").build()).build();
        }

        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client:" + ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.getCause().printStackTrace();
        ctx.close();
    }
}
