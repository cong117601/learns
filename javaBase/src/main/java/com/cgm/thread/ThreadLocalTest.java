package com.cgm.thread;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalTest {



    static ThreadLocal<Map> threadLocal = new ThreadLocal();



    public static void main(String[] args) {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("cookie","cookie");
        map.put("ip","ip");
        threadLocal.set(map);

    }

}
