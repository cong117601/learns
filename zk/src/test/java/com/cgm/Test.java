package com.cgm;

import com.cgm.zk.BaseZkOpt;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.nio.charset.StandardCharsets;

public class Test extends BaseZkOpt {


    @org.junit.Test
    public void test1() throws InterruptedException, KeeperException {
        BaseZkOpt.getZkConnect().create("/t8",
                "abc".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE
                , CreateMode.PERSISTENT);
    }
    @org.junit.Test
    public void test2() throws InterruptedException, KeeperException {
        BaseZkOpt.getZkConnect().delete("/t8", -1);
    }

}
