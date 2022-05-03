package com.cgm.thread.threadPool;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DownUrlTask implements Runnable {
    /**
     * 当前是属于第几段线程
     **/
    private  int pageIndex;

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    private List<String> list;
    private static CountDownLatch countDownLatch;

    public DownUrlTask(int pageIndex, List<String> list, CountDownLatch countDownLatch) {
        this.pageIndex = pageIndex;
        this.list = list;
        this.countDownLatch = countDownLatch;
    }

    public DownUrlTask(int pageIndex, List<String> list) {
        //threadLocal.set(pageIndex);
        this.pageIndex = pageIndex;
        this.list = list;


    }

    @Override
    public void run() {

        try {

//            Integer integer = threadLocal.get();
            downloadImg2(list, "E:\\img\\" + (pageIndex + 1) + "\\");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public static void downloadImg(List<String> urls, String path) throws Exception {
//
//        for (int i = 0; i < urls.size(); i++) {
//            File file1 = new File(path + pageIndex + "_" + i + ".jpg");
//            //先验证当前图片是否已经存在,存在就跳过
//            if (!file1.exists()) {
//                System.out.println("第" + (pageIndex + 1) + "个文件夹中第" + i + "个文件开始");
//                try {
//                    URL url = new URL(urls.get(i));
//                    HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
//                    urlCon.setConnectTimeout(6000);
//                    urlCon.setReadTimeout(8000);
//                    // 读文件
//                    DataInputStream in = new DataInputStream(urlCon.getInputStream());
//
//                    File file = new File(path);
//                    //当前文件夹不存在时，创建
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
//                    //创建图片文件
//                    file1.createNewFile();
//                    DataOutputStream out = new DataOutputStream(new FileOutputStream(file1));
//                    byte[] buffer = new byte[2048];
//                    int count = 0;
//                    while ((count = in.read(buffer)) > 0) {
//                        out.write(buffer, 0, count);
//                    }
//                    out.close();
//                    in.close();
//                    countDownLatch.countDown();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            System.out.println("第" + (pageIndex + 1) + "个文件夹中第" + i + "个文件结束");
//
//        }
//    }

    public static void downloadImg2(List<String> urls, String path) throws Exception {
        File file2 = new File(path);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        for (int i = 0; i < urls.size(); i++) {
            //要创建的文件地址
            String filePath = path + i + ".jpg";
            File file1 = new File(filePath);
            Connection.Response execute = null;
            try {
                execute = Jsoup.connect(urls.get(i)).ignoreContentType(true).execute();
                //保存图片
                //这样写的好处是可以充分关闭，不让文件流占用资源
                FileOutputStream fos = new FileOutputStream(file1);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(execute.bodyAsBytes());
                bos.close();
                fos.close();
            } catch (HttpStatusException httpStatusException) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}