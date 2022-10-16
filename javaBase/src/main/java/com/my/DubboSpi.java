package com.my;


import com.my.dubbo.Protocol;
import com.my.dubbo.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class DubboSpi {

    public static void main(String[] args) {

        ExtensionLoader<Protocol> extensionLoader =
                ExtensionLoader.getExtensionLoader(Protocol.class);

        // http
        Protocol protocol = extensionLoader.getExtension("http");

        protocol.start(new URL("localhost", 8080));
    }
}
