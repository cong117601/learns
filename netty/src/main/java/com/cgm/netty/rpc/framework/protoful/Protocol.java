package com.cgm.netty.rpc.framework.protoful;

import com.cgm.netty.rpc.framework.Invocation;
import com.cgm.netty.rpc.framework.Url;

public interface Protocol {

    void start(Url url);


    String send(Url url, Invocation invocation);
}
