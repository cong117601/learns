package com.cgm.StringTest;

public class Test {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = new String("abc");
        String s3 = s2.intern();

//        System.out.println(s1 == s2);
//        System.out.println(s1 == s3);
//        System.out.println(s2 == s3);


        String str = "hello";
        test(str);
        System.out.println(str);


    }

    public static void test(String str) {

//        str = str + "world";
//
//        System.out.println(str);
//
//        int[] a = new int[1024];


        String s = new String("a") + new String("b"); // new String("ab")
        String s2 = "ab";
        String s1 = s.intern();

        System.out.println(s1 == "ab");
        System.out.println(s == "ab");

        System.out.println(s1 == s2);
        System.out.println(s == s2);
    }


}
