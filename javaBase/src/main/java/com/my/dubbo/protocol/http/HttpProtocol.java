package com.my.dubbo.protocol.http;

import com.mchange.v2.c3p0.jboss.C3P0PooledDataSource;
import com.my.dubbo.Invocation;
import com.my.dubbo.Protocol;
import com.my.dubbo.URL;

public class HttpProtocol implements Protocol {

    @Override
    public void start(URL url) {

        new HttpServer().start(url.getHostname(), url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new HttpClient().send(url.getHostname(),url.getPort(),invocation);
    }
}
