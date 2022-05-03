package com.cgm.proxy.jdk;

public class Test {

    public static void main(String[] args) {
        PeopleService proxyClass = (PeopleService)JDKProxy.getProxyClass(new PeopleServiceImpl());
        proxyClass.eat("apple");
    }
}
