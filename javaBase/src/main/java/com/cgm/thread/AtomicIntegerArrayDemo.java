package com.cgm.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicIntegerArrayDemo {
    public static void main(String[] args) {
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
//        //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
//        //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1,2,3,4,5});
//
//        for (int i = 0; i <atomicIntegerArray.length(); i++) {
//            System.out.println(atomicIntegerArray.get(i));
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        int tmpInt = 0;
//
//        tmpInt = atomicIntegerArray.getAndSet(0,1122);
//        System.out.println(tmpInt+"\t"+atomicIntegerArray.get(0));
//        atomicIntegerArray.getAndIncrement(1);
//        atomicIntegerArray.getAndIncrement(1);
//        tmpInt = atomicIntegerArray.getAndIncrement(1);
//        System.out.println(tmpInt+"\t"+atomicIntegerArray.get(1));



        User z3 = new User("z3",24);
        User li4 = new User("li4",26);

        AtomicReference<User> atomicReferenceUser = new AtomicReference<>();

        atomicReferenceUser.set(z3);
        System.out.println(atomicReferenceUser.compareAndSet(z3,li4)+"\t"+atomicReferenceUser.get().toString());
        System.out.println(atomicReferenceUser.compareAndSet(li4,z3)+"\t"+atomicReferenceUser.get().toString());

    }
}
@Getter
@ToString
@AllArgsConstructor
class User
{
    String userName;
    int    age;
}
