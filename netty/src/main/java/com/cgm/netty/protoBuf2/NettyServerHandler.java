package com.cgm.netty.protoBuf2;

import com.cgm.netty.protoBuf.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 业务处理器
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        StudentPOJO.Student student = (StudentPOJO.Student) msg;
//
//        System.out.println("客户短发送的数据：" + "id:" + student.getId() + " name:" + student.getName());
//
//
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        if (msg.hasStudent()) {
            System.out.println("学生id:" + msg.getStudent().getId() + " 学生名字" + msg.getStudent().getName());
        } else {
            System.out.println("老师age:" + msg.getTeacher().getAge() + " 老师名字" + msg.getTeacher().getTName());
        }
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //writeAndFlush 是 write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵1", CharsetUtil.UTF_8));
    }

    //发生异常后, 一般是需要关闭通道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
