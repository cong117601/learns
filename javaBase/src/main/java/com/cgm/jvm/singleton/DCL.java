package com.cgm.jvm.singleton;

/**
 * 双重检查单列模式
 */
public class DCL {

    private static volatile DCL instance;


    private DCL() {

    }

    public DCL getDcl() {
        if (instance == null) {
            synchronized (DCL.class) {
                if (instance == null) {
                    instance = new DCL();
                }
            }
        }

        return instance;
    }


}
