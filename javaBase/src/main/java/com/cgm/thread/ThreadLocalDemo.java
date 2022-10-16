package com.cgm.thread;

import com.sun.org.apache.xml.internal.security.Init;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;

class House {
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    ThreadLocal<Integer> threadLocal2 =   new ThreadLocal<Integer>();
    public void saleHouse() {
        Integer value = threadLocal.get();
        value++;
        threadLocal.set(value);

    }


}

public class ThreadLocalDemo {

    public static void main(String[] args) {
        House house = new House();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    house.saleHouse();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---" + house.threadLocal.get());
            } finally {
                house.threadLocal.remove();//如果不清理自定义的 ThreadLocal 变量，可能会影响后续业务逻辑和造成内存泄露等问题
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 2; i++) {
                    house.saleHouse();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---" + house.threadLocal.get());
            } finally {
                house.threadLocal.remove();
            }
        }, "t2").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    house.saleHouse();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "---" + house.threadLocal.get());
            } finally {
                house.threadLocal.remove();
            }
        }, "t3").start();


        System.out.println(Thread.currentThread().getName() + "\t" + "---" + house.threadLocal.get());

    }

}
