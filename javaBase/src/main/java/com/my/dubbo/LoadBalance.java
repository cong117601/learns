package com.my.dubbo;

import java.util.List;
import java.util.Random;

public class LoadBalance {


    public static URL getUrl(List<URL> urls) {

        Random random = new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);

    }
}
