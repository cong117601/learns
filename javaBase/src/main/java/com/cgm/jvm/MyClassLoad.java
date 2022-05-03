package com.cgm.jvm;

import java.io.*;

public class MyClassLoad extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {


        File file = new File("E:\\learns\\javaBase\\src\\main\\java\\" + name.replace(".","\\")+".class");

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int b = 0;
            while ((b = fis.read()) != 0) {
                baos.write(b);
            }
            byte[] bytes = baos.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }


    public static void main(String[] args) throws Exception {
        ClassLoader myClassLoad = new MyClassLoad();
        Class<?> aClass = myClassLoad.loadClass("com.cgm.jvm.Hello");
        Hello o = (Hello) aClass.newInstance();
        o.m();
        System.out.println(myClassLoad.getClass().getClassLoader());
        System.out.println(myClassLoad.getParent().getParent());
    }
}
