package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

public interface BuyerService {
    /**
     *  查询一个订单
     * @param openid 微信openid
     * @param orderId 订单id
     * @return
     */
    OrderDTO findOrderOne(String openid,String orderId);

    /**
     * 取消订单
     * @param openid 微信openid
     * @param orderId 订单id
     * @return
     */
    OrderDTO cancelOrder(String openid,String orderId);
}
