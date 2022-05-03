package com.cgm.netty.chat.tcp;

/**
 * 读取协议
 * 带 数据长度
 */
public class MyMessageProtocol {

    private int len;

    private byte[] message;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }
}
