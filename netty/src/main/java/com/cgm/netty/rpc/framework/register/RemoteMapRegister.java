package com.cgm.netty.rpc.framework.register;

import com.cgm.netty.rpc.framework.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteMapRegister {


    private static Map<String, List<Url>> map = new HashMap<>();


    public static void regist(String interFaceName,Url url){
        List<Url> urls = map.get(interFaceName);
        if(urls==null){
            urls = new ArrayList<>();
        }
        urls.add(url);

        map.put(interFaceName,urls);
    }



    public static List<Url> get(String interFaceName){
        List<Url> urls = map.get(interFaceName);
        return urls;
    }


}
