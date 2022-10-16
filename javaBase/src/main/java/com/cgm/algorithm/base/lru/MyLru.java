package com.cgm.algorithm.base.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MyLru extends LinkedHashMap {
    private int capacity;

    public MyLru(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity * 0.75f;
    }

}
