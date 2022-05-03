package com.cgm.trycatch;

public class Test {

    public static void main(String[] args) {

        System.out.println(a());
    }

    public static int a() {
        try {
            System.out.println(11111);
            System.out.println(22222);
            System.out.println(33333);
            System.out.println(55);
            int c = 10;
            int i = 1 / 0;
            return c;

        } catch (Exception e) {

            e.printStackTrace();
            return 1;
        } finally {
            System.out.println(2);
        }


    }

}
