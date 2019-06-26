package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderMasterService;
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
public class PushMessageServiceImplTest {
    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Autowired
    private OrderMasterService orderMasterService;
    @Test
    public void orderStatus() {
        OrderDTO one = orderMasterService.findOne("123456");
        pushMessageService.orderStatus(one);

    }
}