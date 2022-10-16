package com.cgm.thread.threadPool;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test3 {
    static List<String> urlList = new ArrayList<>();
    static String threadName = "downLoad";
    static int core = 2;
    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(core, 10, 100, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), new MyThreadFactory(threadName), new ThreadPoolExecutor.DiscardOldestPolicy());


    public static void uploadImg(int sunSum) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(sunSum);
        int size = urlList.size();

        /**将集合切分的段数**/
        int listStart, listEnd;

        /**定义子线程**/
        DownUrlTask task = null;
        //拆分后的数量
        int page = size % sunSum != 0 ? ((size / sunSum) + 1) : size % sunSum;
        /**将list 切分10份 多线程执行**/
        for (int i = 0; i < page; i++) {
            /***计算切割  开始和结束**/
            listStart = i * sunSum;
            listEnd = (i + 1) * sunSum;
            if (listEnd > size) {
                listEnd = size;
            }
            /**线程切断**/
            List<String> sunList = urlList.subList(listStart, listEnd);
            /**子线程初始化**/
            task = new DownUrlTask(i, sunList, countDownLatch);
            /***多线程执行***/
            threadPool.submit(task);


        }
        countDownLatch.await();
        threadPool.shutdown();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        //1. 创建httpClient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        //2. 创建httpGet请求，并进行相关的配置
//        HttpGet httpGet = new HttpGet("http://www.itcast.cn/?username=java");
//        httpGet.setHeader("Pragma","no-cache");
//        //3. 发起请求
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//        //4.判断响应状态码，并获取响应数据
//        if(response.getStatusLine().getStatusCode()==200){
//            String html = EntityUtils.toString(response.getEntity(),"utf-8");
//            System.out.println(html);
//        }
//        //5. 关闭资源
//        httpClient.close();
//        response.close();


//        Document htmlStr = Jsoup.connect("https://www.itcast.cn/").get();
//        Elements elements = htmlStr.getElementsByClass("mask_img1");
//        File file = new File("C:\\Users\\cong1\\Desktop\\1.txt");
//        OutputStream outputStream = new FileOutputStream(file);
//        for (int i = 0; i < elements.size(); i++) {
//            String attr = elements.get(i).attr("src");
//            outputStream.write(attr.getBytes());
//            outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
//            urlList.add(attr);
//        }
//        outputStream.close();
//
//        System.out.println(urlList.size());

        File file = new File("C:\\Users\\cong1\\Desktop\\1.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = null;

        while ((line = br.readLine()) != null) {

            urlList.add(line);
        }

        br.close();
        System.out.println("图片个数" + urlList.size());
        uploadImg(10);
    }

}
