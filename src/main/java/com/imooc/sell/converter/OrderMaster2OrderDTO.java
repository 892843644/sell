package com.imooc.sell.converter;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderMasterEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTO {
    public static OrderDTO convert(OrderMasterEntity orderMasterEntity){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMasterEntity,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMasterEntity> orderMasterEntityList){
        List<OrderDTO> orderDTOList = orderMasterEntityList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return orderDTOList;
    }
}
