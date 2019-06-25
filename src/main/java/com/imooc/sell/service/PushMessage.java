package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**消息推送
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/6/25 15:13
 */
public interface PushMessage {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
