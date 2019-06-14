package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.entity.OrderMasterEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderDetailServiceImplTest {
    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @Test
    public void saveTest(){
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setDetailId("123456");
        orderDetailEntity.setOrderId("123456");
        orderDetailEntity.setProductId("123456");
        orderDetailEntity.setProductName("皮蛋粥");
        orderDetailEntity.setProductPrice(new BigDecimal(3.5));
        orderDetailEntity.setProductQuantity(11);
        orderDetailEntity.setProductIcon("http://xxx.jpg");
        orderDetailService.save(orderDetailEntity);

    }
    @Test
    public void findByOrderIDtest(){
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",123456);
        List<OrderDetailEntity> list = orderDetailService.list(queryWrapper);
        log.info(list+"");
    }
}