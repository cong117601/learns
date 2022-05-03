package com.cgm.zk;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLock {


    private static int num = 10;

    private static ZooKeeper zk;

    @Before
    public void conn() {
        zk = BaseZkOpt.getZkConnect();
    }

    @After
    public void close() {
        BaseZkOpt.close();
    }

    @Test
    public void lock() {
        for (int i = 0; i < num; i++) {
            //开启线程抢锁
            new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                //抢锁 只有抢到锁的线程往下走，其他阻塞
                WatchCallBack watchCallBack = new WatchCallBack(zk, threadName);
                watchCallBack.tryLock();
                //干活
                System.out.println("gan huo.....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //释放锁
                watchCallBack.unLock();

            }).start();


        }
        while (true) {

        }
    }


}
