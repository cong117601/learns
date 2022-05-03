package com.cgm.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * zk 基础操作
 */
public class BaseZkOpt {

    private static final String ZK_ADDRESS = "192.168.139.138:2181/zookeeperLock";

    private static final int SESSION_TIMEOUT = 30000;


    private static ZooKeeper zooKeeper = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    static ThreadLocal<ZooKeeper> tl = new ThreadLocal<>();
    //创建连接
    private static Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if (event.getState() == Event.KeeperState.SyncConnected
                    && event.getType() == Event.EventType.None) {
                countDownLatch.countDown();
                System.out.println("建立连接");
            }
        }
    };

    public static ZooKeeper getZkConnect() {
        try {
            zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, watcher);
            System.out.println("建立中。。。。");
            countDownLatch.await();
            return zooKeeper;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //添加节点
    public void Create(String path, String data) {
        try {
            getZkConnect().create(path, data.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //修改节点

    //删除节点

    //关闭连接
    public static void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
