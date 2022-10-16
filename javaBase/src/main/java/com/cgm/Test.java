package com.cgm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<Integer> integers = new ArrayList<>( Arrays.asList(1, 2, 3));
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            iterator.remove();
        }
    }
}
