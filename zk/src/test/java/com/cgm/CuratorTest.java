package com.cgm;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class CuratorTest {

    CuratorFramework client = null;

    @Before
    public void test1() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("192.168.139.138:2181", retryPolicy);
        client.start();
    }

    @Test
    public void test2() {
        String pathWithParent = "/node-parent/sub-node-1";
        try {
            // String path = client.create().creatingParentsIfNeeded().forPath(pathWithParent);
            client.create().withMode(CreateMode.PERSISTENT).forPath("/curator-node", "abc".getBytes(StandardCharsets.UTF_8));
            // System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test3() throws Exception {
        byte[] bytes = client.getData().forPath("/curator-node");
        System.out.println(new String(bytes));
    }

    @Test
    public void test4() throws Exception {
        client.getData().inBackground((item1, item2) -> {
            System.out.println(item2);
        }).forPath("/curator-node");
        System.in.read();
    }

    @Test
    public void test5() throws Exception {
        client.setData().inBackground((item1, item2) -> {
            System.out.println(item2);
        }).forPath("/curator-node", "cgm".getBytes(StandardCharsets.UTF_8));
        System.in.read();
    }

}
