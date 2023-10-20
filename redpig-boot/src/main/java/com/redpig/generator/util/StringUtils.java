package com.redpig.generator.util;

public class StringUtils {

    public static void main(String[] args) {
        String name = "usenr_name_uu_u_";
        //找到下划线并且把下划线后面的字母改成 大写
        String anotherName=getName(name);
        System.out.println("转化前："+name);
        System.out.println("转化后："+anotherName);

    }

    public static String getName(String name){
        return getName(name,name).replace("_","");
    }

    private static String getName(String name,String  anotherName) {
        name=anotherName;
        //如果最后一个是_ 不做转换
        if(name.indexOf("_")>0&&name.length()!=name.indexOf("_")+1){
            int lengthPlace=name.indexOf("_");
            name=name.replaceFirst("_", "");
            String s=name.substring(lengthPlace, lengthPlace+1);
            s=s.toUpperCase();
            anotherName=name.substring(0,lengthPlace)+s+name.substring(lengthPlace+1);
        }else{
            return  anotherName;
        }
        return getName(name,anotherName);
    }
}