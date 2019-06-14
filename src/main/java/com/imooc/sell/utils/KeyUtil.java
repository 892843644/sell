package com.imooc.sell.utils;



import java.util.Random;


public class KeyUtil {
    //得到时间毫秒数+6位随机数字拼接字符串
    public static synchronized String getUniqueKey(){
        Random random=new Random();
        //生成6为随机数
       Integer number= random.nextInt(900000)+100000;

       //返回时间毫秒数+6位随机数字拼接字符串
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
