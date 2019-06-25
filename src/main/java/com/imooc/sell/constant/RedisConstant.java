package com.imooc.sell.constant;

/**
 * @Author ：cjy
 * @description ：redis常量
 * @CreateTime ：Created in 2019/6/24 21:39
 */
public interface RedisConstant {
    String TOKEN_PREFIX="token_%s";
    Integer EXPIRE=7200; //单位秒
}
