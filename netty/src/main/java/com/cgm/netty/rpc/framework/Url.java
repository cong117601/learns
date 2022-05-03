package com.cgm.netty.rpc.framework;

public class Url {


    private String hostName;

    private int port;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Url(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }
}
