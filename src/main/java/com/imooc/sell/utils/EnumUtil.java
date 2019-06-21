package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

/** 枚举工具类
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/6/20 21:36
 */
public class EnumUtil {

    /**
     * 通过枚举值获得对应的枚举
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()){
           if(code.equals(each.getCode())){
                return each;
           }
        }
        return null;
    }
}
