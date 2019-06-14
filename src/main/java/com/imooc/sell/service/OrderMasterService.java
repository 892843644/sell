package com.imooc.sell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderMasterEntity;

public interface OrderMasterService extends IService<OrderMasterEntity>{
    /**
     *  创建订单
     * @param orderDTO 用来接受前台传来的参数
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 通过订单id查询每个订单对应的订单详情集合结果封装对象
     * @param orderId 订单id
     * @return 每个订单对应的订单详情集合结果封装对象
     */
    OrderDTO findOne(String orderId);

    /**
     *  取消订单
     * @param orderDTO
     * @return
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    OrderDTO paid(OrderDTO orderDTO);

}
