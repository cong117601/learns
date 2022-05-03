package com.cgm.reflection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 加载class的方式：
 *  Class.forName
 *  类名.class
 *  对象.class
 *  类加载器 xxxClassLoader.loadClass() 传入类路径获取Class对象
 */

public class ReflectionTest {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("re.properties");
        properties.load(is);
        String classfullpath = properties.getProperty("classfullpath").toString();
        String method = properties.getProperty("method").toString();

        Class<?> aClass = Class.forName(classfullpath);
        User o = (User) aClass.newInstance();

        Constructor<?>[] constructors = aClass.getConstructors();
        System.out.println(constructors.length);
        Log declaredAnnotation = aClass.getDeclaredAnnotation(Log.class);
        System.out.println(declaredAnnotation.value());
        Method method1 = aClass.getMethod(method);
        method1.invoke(o);

    }
}
