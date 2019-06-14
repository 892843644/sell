package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.sell.dao.OrderDetailDAO;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDAO,OrderDetailEntity> implements OrderDetailService{
}
