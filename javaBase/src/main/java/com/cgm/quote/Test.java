package com.cgm.quote;

import com.cgm.reflection.User;

public class Test {


    public static void main(String[] args) {

        User user = new User();
        user.setAge(10);
        user.setName("cgm");
        m(user);
        System.out.println(user);

        int a = 10;

        setValue(a);
        System.out.println(a);

    }


    public static void m(User user) {

        user.setAge(200);
        user.setName("sdasd");

    }


    public static void setValue(int a) {
        a = 100;
        System.out.println(a);

    }
}
