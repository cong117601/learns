package com.cgm.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SafeFailDemo {
    public static void main(String[] args) {

        //CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals("1")) {
                iterator.remove();
            }


        }


    }
}
