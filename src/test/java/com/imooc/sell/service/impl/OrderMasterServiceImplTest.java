package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.entity.OrderMasterEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {
    @Autowired
    private OrderMasterServiceImpl orderMasterService;
    private String openId="110110";
    @Test
    public void saveTest(){
        OrderMasterEntity orderMasterEntity = new OrderMasterEntity();
        orderMasterEntity.setOrderId("1234567");
        orderMasterEntity.setBuyerName("超超");
        orderMasterEntity.setBuyerPhone("18888866666");
        orderMasterEntity.setBuyerAddress("工业大学");
        orderMasterEntity.setBuyerOpenid(openId);
        orderMasterEntity.setOrderAmount(new BigDecimal(2.5));
        boolean save = orderMasterService.save(orderMasterEntity);

    }

    @Test
    public void findByBuyerOpenId(){
        QueryWrapper<OrderMasterEntity> queryWrapper = new QueryWrapper<>();
        IPage<OrderMasterEntity> OrderMasterEntityPage=new Page<>(2,2);
        IPage<OrderDTO> OrderDTOPage=new Page<>();

        queryWrapper.eq("buyer_openid",openId);
        OrderMasterEntityPage = orderMasterService.page(OrderMasterEntityPage, queryWrapper);
        BeanUtils.copyProperties(OrderMasterEntityPage,OrderDTOPage);
        log.info(OrderDTOPage.getRecords()+"");
    }

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("李四a ");
        orderDTO.setBuyerPhone("18649011111");
        orderDTO.setBuyerAddress("工大");
        orderDTO.setBuyerOpenid("11010");

        List<OrderDetailEntity> orderDetailEntityList=new ArrayList<>();

        OrderDetailEntity orderDetailEntity=new OrderDetailEntity();
        orderDetailEntity.setProductId("123459");
        orderDetailEntity.setProductQuantity(1);


        OrderDetailEntity orderDetailEntity2=new OrderDetailEntity();
        orderDetailEntity2.setProductId("123458");
        orderDetailEntity2.setProductQuantity(2);

        orderDetailEntityList.add(orderDetailEntity);
        orderDetailEntityList.add(orderDetailEntity2);
        orderDTO.setOrderDetailEntityList(orderDetailEntityList);

        OrderDTO  result = orderMasterService.create(orderDTO);
        log.info(result+"");
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderMasterService.findOne("123456");
        log.info(orderDTO+"");
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderMasterService.findOne("123456");
        OrderDTO result = orderMasterService.cancel(orderDTO);
        log.info("result:"+result);
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderMasterService.findOne("123456");
        OrderDTO result = orderMasterService.finish(orderDTO);
        log.info("result:"+result);
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderMasterService.findOne("123456");
        OrderDTO result = orderMasterService.paid(orderDTO);
        log.info("result:"+result);
    }
}