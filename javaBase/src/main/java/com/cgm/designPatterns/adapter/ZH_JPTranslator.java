package com.cgm.designPatterns.adapter;

public class ZH_JPTranslator implements Translator{
    @Override
    public String translator(String context) {
        if("你好".equals(context)){
            return "上本伊马斯";
        }
        if("什么".equals(context)){
            return "nani";
        }
        return "11111";
    }
}
