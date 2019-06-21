package com.imooc.sell.utils;

public class MathUtil {

    private static final Double Money_Range=0.01;

    /**
     * 判断精度 比较两个金额是否相等
     * @param a
     * @param b
     * @return
     */
    public static Boolean equal(Double a,Double b){
        Double abs = Math.abs(a - b);
        if(abs<Money_Range){
            return true;
        }
        return false;
    }
}
