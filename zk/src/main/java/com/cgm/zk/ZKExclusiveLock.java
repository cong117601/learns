package com.cgm.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * zk 排他锁
 * 大致思路：
 * <p>
 * 1.多线程到zkServer创建一个节点，谁能够创建成功就 说明加锁成功，未创建成功的则进行等待，并监听该节点
 * 2.当业务逻辑执行完之后，就删除这个节点，并通知唤醒争抢锁的线程，之后这些线程就 再次重复 1步骤
 * <p>
 * 需要注意的点：
 * 1.节点类型选择：
 * 若是使用持久节点，当client成功 创建锁之后，如果client 挂了，那持久节点不会被删除，会造成死锁的问题
 * 2.为了解决死锁问题：可以使用临时节点
 * <p>
 * 3.若使用临时节点之后，当业务逻辑执行完之后，zkServer会发送通知给到监听该节点的client，如果client数据很多，会给zkServer造成很大的压力。出现羊群效应。
 * <p>
 * 4.为了解决羊群效应，故使用临时顺序节点
 * 多个线程进来加锁，首先会创建临时顺序，如果说 顺序是最小的那一个，则直接加锁成功，业务执行完后，会删除该节点
 * 后面的线程会 对它所创建的节点的上一个进行监听，如果收到删除事件，则会判断 自己所在的节点位置 是不是最小，如果是，则加锁成功，不是继续等待！
 * 该种方式也是 公平锁，什么时候 抢锁关键在于创建临时节点的位置。
 * <p>
 * 5.需要了解，如果client 发送到zkServer的命令执行成功，由于网络等因素没有收到服务端发送的消息，那客户端会重试，重新创建新的节点并监听上一个节点，上一个节点 可是幽灵节点，client永远也不会知道。
 * <p>
 * 这就可能会造成死锁问题；
 * <p>
 * 可以使用在创建节点的时候，给这个节点带一个唯一标识，如果发生重试，会根据这个标识进行重试，如果zkServer中有这个标识，则不用重建。
 */
public class ZKExclusiveLock {


    //模拟多个线程 买票
    private static int story = 1;

    private static int num = 10;

    private static CountDownLatch cdl = new CountDownLatch(num);


    private static String zkAddress = "192.168.139.138:2181";

    private static String path = "/lock";

    private static CuratorFramework curatorFramework = null;
    private static InterProcessMutex lock = null;

    public static void main(String[] args) {

        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                try {
                    cdl.await();
                    lock = new InterProcessMutex(getCuratorFramework(), path);
                    if (lock.acquire(5, TimeUnit.SECONDS)) {
                        if (story > 0) {
                            Thread.sleep(50);
                            story--;
                        }
                        System.out.println(story);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        System.out.println("111");
                    }
                }

            }).start();
            cdl.countDown();
        }


    }


    public static CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        client.start();
        return client;
    }


}
