package com.cgm.enum2;

public final class Testenum {

    enum SingletonDemo{

        INSTANCE;
        private SingleClass single;
        private SingletonDemo(){
            single = new SingleClass();
            single.setName("chx");
        }
        public SingleClass getsingle(){
            return single;
        }


    }


    public static void main(String[] args) {
        SingleClass getsingle = SingletonDemo.INSTANCE.getsingle();
    }
}
