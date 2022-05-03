package com.cgm.thread.threadPool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class WaringTask implements Callable {

    /**
     * 当前是属于第几段线程
     **/
    private int pageIndex;

    private List<Integer> list;


    private CountDownLatch countDownLatch;

    public WaringTask(int pageIndex, List<Integer> list, CountDownLatch countDownLatch) {
        this.pageIndex = pageIndex;
        this.list = list;
        this.countDownLatch = countDownLatch;
    }

    int count = 0;

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        Boolean result = Boolean.TRUE;
        if (null != list && list.size() > 0) {
            for (Integer str : list) {
                try {
                    count += str;
                } catch (Exception e) {
                    result = Boolean.FALSE;
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }
        return count;
    }

    /**
     * 业务处理
     *
     * @param str
     * @throws InterruptedException
     */
//    public void handleStr(String str) throws InterruptedException {
//        Thread.sleep(1000);
//    }
}
