package com.cgm.zk;

import io.netty.handler.timeout.IdleState;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class WatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    private ZooKeeper zk;
    private String name;
    private String pathName;


    private CountDownLatch cdl = new CountDownLatch(1);

    public WatchCallBack(ZooKeeper zk, String name) {
        this.zk = zk;
        this.name = name;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }


    public void tryLock() {

        zk.create("/lock", name.getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE
                , CreateMode.EPHEMERAL_SEQUENTIAL, this, "avc");

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void unLock() {
        try {
            System.out.println("delete: " + pathName);
            zk.delete(pathName, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                System.out.println("NodeDeleted " + name);
                zk.getChildren("/", false, this, "sdf");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
        }


    }

    //create callback
    @Override
    public void processResult(int i, String path, Object ctx, String name) {
        if (name != null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.name + " create node: " + name);
            pathName = name;
            //十个线程都 查看获取父目录下的节点
            zk.getChildren("/", false, this, "sdf");
        }
    }

    // getChildren callback
    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //创建节点完成后，之后到获取子节点，之后到此回调处，查看线程能看到的前面的节点的样子  无序的
//        System.out.println(this.name +" look locks...."+list.size());
//        for (String child : list) {
//            System.out.println(child);
//        }
        Collections.sort(list);
        int index = list.indexOf(pathName.substring(1));
        if (index == 0) {
            System.out.println(this.name + " " + pathName + " i am first....");
            cdl.countDown();
        } else {
            //监听前面的节点  C
            System.out.println(name + " watcher " + list.get(i - 1));
            zk.exists("/" + list.get(i - 1), this, this, "sdf");

        }
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {

    }
}
