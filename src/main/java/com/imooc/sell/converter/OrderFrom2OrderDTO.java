package com.imooc.sell.converter;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderFrom;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class OrderFrom2OrderDTO {
    /**
     *   OrderFrom2OrderDTO
     * @param orderFrom 前端传来的数据
     * @return OrderDTO
     */
    public static OrderDTO convert(OrderFrom orderFrom){

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderFrom.getName());
        orderDTO.setBuyerPhone(orderFrom.getPhone());
        orderDTO.setBuyerAddress(orderFrom.getAddress());
        orderDTO.setBuyerOpenid(orderFrom.getOpenid());
        try {

            String jsonStr= orderFrom.getItems();
            //把json字符串转换成 List<OrderDetailEntity>
            List<OrderDetailEntity> orderDetailEntity =JSONObject.parseArray(jsonStr,OrderDetailEntity.class);
            log.info("orderDetailEntity:"+orderDetailEntity);
            orderDTO.setOrderDetailEntityList(orderDetailEntity);
            log.info("orderDTO:"+orderDTO);
        }catch (Exception e){
            log.error("【对象转换】错误 ,string={}",orderFrom);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        return orderDTO;
    }
}
