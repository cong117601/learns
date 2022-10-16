package com.cgm.aqs;

public class SyncDemo {


    public static void main(String[] args) {



        synchronized (SyncDemo.class){

            System.out.println("enter 1");


            synchronized (SyncDemo.class){


                System.out.println("enter 2");
                int i = 1/0;
                System.out.println("enter 2");
            }

            System.out.println("exit 1");
        }
    }
}
