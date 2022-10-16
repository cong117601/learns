package com.cgm.designPatterns.singleton;

import java.util.HashMap;
import java.util.Map;

public class SingleFactory<T> {
    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static Map<Class<? extends SingleFactory>, SingleFactory> INSTANCES_MAP = new HashMap<>();

    /*
     * 无参构造方法
     * @attention: 这里不能再用private修饰了，因为该类是父类
     * @date: 2020年09月18日 0018 10:20
     */
    protected SingleFactory() {
    }

    /*
     * 单例（懒汉式）
     * @date: 2020年07月13日 0013 19:50
     * @param: instanceClass
     * @return: com.xyh.bill.service.tools.Singleton
     */
    public synchronized static <E extends SingleFactory> SingleFactory getInstance(Class<E> instanceClass) throws Exception {
        if (INSTANCES_MAP.containsKey(instanceClass)) {
            return (E) INSTANCES_MAP.get(instanceClass);
        } else {
            E instance = instanceClass.newInstance();
            INSTANCES_MAP.put(instanceClass, instance);
            return instance;
        }
    }
}
