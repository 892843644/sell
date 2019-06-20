package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
    @Autowired
    private PayServiceImpl payService;
    @Autowired
    private OrderMasterServiceImpl orderMasterService;
    @Test
    public void create() {

        OrderDTO orderDTO = orderMasterService.findOne("123456");
        payService.create(orderDTO);
    }
}