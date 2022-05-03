package com.cgm.proxy.jdk;

public class PeopleServiceImpl implements PeopleService{
    @Override
    public void shuaYa() {
        System.out.println("开始刷牙");
    }

    @Override
    public String eat(String food) {
        System.out.println("开始吃:"+food);
        return food;
    }

    @Override
    public void sleep() {
        System.out.println("开始睡觉");
    }
}
