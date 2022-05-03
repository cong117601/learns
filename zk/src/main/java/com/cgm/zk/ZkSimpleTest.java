package com.cgm.zk;

import com.alibaba.fastjson.JSONObject;
import com.cgm.config.MyConfig;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

//http://note.youdao.com/noteshare?id=4b98050e193ba49f993268891f1a607e&sub=FD150E1D7C7645EF9DB40B0E920AB987
//http://note.youdao.com/noteshare?id=16ab5da92ee89d93f948dc57762b7ae6&sub=D37AC1F910A04A2EAEC83881AB0F32A1

/**
 * 分布式协调组件
 * 文件系统数据结构，监听通知机制
 * zk：节点类型：
 * 1.持久化节点
 * 2.持久化顺序节点
 * 3.临时节点
 * 4.临时顺序节点
 * 5.TTL节点 过期删除
 * 6.容器节点 当没有子节点会删除
 * <p>
 * <p>
 * <p>
 * 监听机制：
 * 1.客户端注册监听的相关节点，目录节点或子目录节点
 * 当节点发生变化时，zk会通知客户端
 * <p>
 * 应用场景：
 * 分布式配置中心
 * 注册中心
 * 分布式锁
 * 分布式队列
 * 集群选举
 * 发布订阅
 * 分布式屏障
 * <p>
 * <p>
 * ACL: 权限控制 ： 权限模式 ，授权对象（ID），权限对象
 */
//https://note.youdao.com/ynoteshare/index.html?id=16ab5da92ee89d93f948dc57762b7ae6&type=note&_time=1648133039077
//https://note.youdao.com/ynoteshare/index.html?id=4b98050e193ba49f993268891f1a607e&type=note&_time=1648130914801
//http://note.youdao.com/noteshare?id=80d7bcb710a0e21616248239b9dfd40d&sub=3E9635FCEEB5449B80885533C285C94B 应用
public class ZkSimpleTest {
    private static final String ZK_ADDRESS = "192.168.139.138:2181";

    private static final int SESSION_TIMEOUT = 5000;

    private static ZooKeeper zooKeeper;


    public static void main(String[] args) throws IOException, InterruptedException {


        final CountDownLatch countDownLatch = new CountDownLatch(1);
        zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected &&
                    event.getType() == Watcher.Event.EventType.None) {
                countDownLatch.countDown();
                System.out.println("连接成功");
            }
        });
        System.out.println("连接中....");
        countDownLatch.await();
        //处理业务
        MyConfig myConfig = new MyConfig();
        myConfig.setKey("key");
        myConfig.setName("anyName");
        try {
            String myconfig = zooKeeper.create("/myconfig", JSONObject.toJSONString(myConfig).getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            Watcher wacher = new Watcher() {

                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeDataChanged &&
                            event.getPath() != null && event.getPath().equals("/myconfig")) {
                        System.out.println("发生数据变化");

                        try {
                            byte[] data = zooKeeper.getData("/myconfig", this, null);
                            System.out.println("数据发生变化:" + JSONObject.toJSON(new String(data)));
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            byte[] data = zooKeeper.getData("/myconfig", wacher, null);

            System.out.println("原始数据" + JSONObject.toJSON(new String(data)));
        } catch (KeeperException e) {
            e.printStackTrace();
        }


        System.in.read();
    }
}

