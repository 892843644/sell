package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.sell.dao.OrderMasterDAO;
import com.imooc.sell.dao.ProductInfoDAO;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.entity.OrderMasterEntity;
import com.imooc.sell.entity.ProductInfoEntity;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterDAO,OrderMasterEntity> implements OrderMasterService{
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @Autowired
    private  OrderMasterServiceImpl orderMasterService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //最开始就生成订单id
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1.查询商品购买数量和价格计算总价
        for(OrderDetailEntity orderDetailEntity:orderDTO.getOrderDetailEntityList()){
            //从订单详情表中获得商品id
            String productId = orderDetailEntity.getProductId();
            ProductInfoEntity productInfoEntity = productInfoService.getById(productId);

            //判断商品是否存在
            if(productInfoEntity==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

        //2.计算订单总价
            orderAmount= productInfoEntity.getProductPrice()
                    .multiply(new BigDecimal(orderDetailEntity.getProductQuantity()))
                    .add(orderAmount);
        //3.写入数据库
        //订单详情表入库
            orderDetailEntity.setDetailId(KeyUtil.getUniqueKey());
            orderDetailEntity.setOrderId(orderId);
            //把商品详情对应的字段值拷贝到订单详情对应字段
            BeanUtils.copyProperties(productInfoEntity,orderDetailEntity);
            orderDetailService.save(orderDetailEntity);
        }

        //3.写入数据库
        //订单主表入库
        OrderMasterEntity orderMasterEntity = new OrderMasterEntity();
        System.out.println(orderMasterEntity);
        //先拷贝在set其他方法 以免被覆盖
        BeanUtils.copyProperties(orderDTO,orderMasterEntity);
        orderMasterEntity.setOrderAmount(orderAmount);
        orderMasterEntity.setOrderId(orderId);
        orderMasterEntity.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterEntity.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMasterService.save(orderMasterEntity);

        //4.扣库存
        //得到一个购物车集合
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailEntityList().stream().
                map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        //扣除库存
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        //查询订单主表并判断是否存在
        OrderMasterEntity orderMasterEntity = this.getById(orderId);
        if(orderMasterEntity==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //通过订单id查询订单详情表
        QueryWrapper<OrderDetailEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        List<OrderDetailEntity> orderDetailEntityList = orderDetailService.list(queryWrapper);
        //判断符合条件的订单详情表数据集合是否为空和长度是否为0
        if(CollectionUtils.isEmpty(orderDetailEntityList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        //把订单主表的属性值复制给orderDTO
        BeanUtils.copyProperties(orderMasterEntity,orderDTO);
        orderDTO.setOrderDetailEntityList(orderDetailEntityList);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMasterEntity orderMasterEntity=new OrderMasterEntity();

        //判断订单状态
        Integer orderStatus = orderDTO.getOrderStatus();
        if(!orderStatus.equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不对，orderId={},orderStatus={}",orderDTO.getOrderId(),orderStatus);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMasterEntity);
        boolean save = orderMasterService.updateById(orderMasterEntity);
        if(save==false){
            log.error("【取消订单】更新失败，orderMasterEntity={}",orderMasterEntity);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        //判断订单详情是否存在
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailEntityList())){
            log.error("【取消订单】订单中无商品详情 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //获取商品详情
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailEntityList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不对 ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMasterEntity orderMasterEntity=new OrderMasterEntity();
        BeanUtils.copyProperties(orderDTO,orderMasterEntity);
        boolean save = this.updateById(orderMasterEntity);
        if(save==false){
            log.error("【完结订单】更新失败，orderMasterEntity={}",orderMasterEntity);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单成功】 订单状态不正确 ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单成功】订单支付状态不正确， orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMasterEntity orderMasterEntity=new OrderMasterEntity();
        BeanUtils.copyProperties(orderDTO,orderMasterEntity);
        boolean save = this.updateById(orderMasterEntity);
        if(save==false){
            log.error("【支付订单成功】更新失败，orderMasterEntity={}",orderMasterEntity);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
