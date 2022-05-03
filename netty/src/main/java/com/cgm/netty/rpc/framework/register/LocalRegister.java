package com.cgm.netty.rpc.framework.register;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地注册中心
 */
public class LocalRegister {

    private static Map<String, Class> map = new HashMap<>();


    public static void regist(String interFaceName, Class implClass) {
        map.put(interFaceName, implClass);
    }

    public static Class get(String interFaceName) {
        return map.get(interFaceName);
    }
}
