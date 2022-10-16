package com.cgm.thread;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ProductComparePrice {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("tmall"),
            new NetMall("pdd"),
            new NetMall("mi")
    );

    public static List<String> findPriceSync(List<NetMall> list, String productName) {
        return list.stream().map(mall -> String.format(productName + " %s price is %.2f", mall.getNetMallName(), mall.getPriceByName(productName))).collect(Collectors.toList());
    }

    public static List<String> findPriceASync(List<NetMall> list, String productName) {
        return list.stream()
                .map(mall ->
                        CompletableFuture.supplyAsync(() -> String.format(productName + " %s price is %.2f", mall.getNetMallName(), mall.getPriceByName(productName))))
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list1 = findPriceASync(list, "thinking in java");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: " + (endTime - startTime) + " 毫秒");
    }
}

@Data
class NetMall {

    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double getPriceByName(String productName) {
        return calcPrice(productName);
    }

    private double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() + productName.charAt(0);
    }

}
