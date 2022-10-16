package com.cgm.trycatch;

public class Test {

    public static void main(String[] args) {

//        System.out.println(a());
//        try {
//            System.out.println(b());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        //[0,1)
        //[0,9) + 1 = [1,10) * 100000
        for (int i = 0; i < 10; i++) {
            System.out.println( (int)((Math.random()*9+1) * Math.pow(10,5)));
        }



    }

    public static int a() {
        try {
            int c = 10;
            int i = 1 / 0;
            return c;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("1111");
            return 3;
        } finally {
            System.out.println(2);

        }


    }

    public static int b() {
        try {
            int b = 1 / 0;
            return 1;
        } catch (Exception e) {
            throw new RuntimeException("by zero");
        }
    }
}
