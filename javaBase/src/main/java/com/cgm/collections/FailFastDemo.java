package com.cgm.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FailFastDemo {


    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("A",1);
        map.put("B",1);
        map.put("C",1);
        map.put("D",1);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {

            Map.Entry<String, Integer> next = iterator.next();
            String key = next.getKey();
            if (key.equals("A")) {
                map.remove(key);
            }

        }


    }
}
