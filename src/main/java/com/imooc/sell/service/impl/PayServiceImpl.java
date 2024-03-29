package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.PayService;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME="微信点餐订单";
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderMasterServiceImpl orderMasterService;
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】, 发起支付 request={}",payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】, 发起支付 response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名 asyncNotify会判断
        //2.支付的状态 asyncNotify会判断
        //3.支付金额
        //4.支付人（下单人==支付人） 这里不判断
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.error("【微信支付】 异步通知 ,payResponse={}",JsonUtil.toJson(payResponse));
        //查询订单
        OrderDTO orderDTO = orderMasterService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(orderDTO==null){
            log.error("【微信支付】 异步通知 订单不存在,orderId={}",payResponse.getOrderId());

            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //判断金额是否一致
        if(!MathUtil.equal(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】 异步通知 订单金额不一致 orderId={},微信通知金额={}，系统金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //修改订单支付状态
        orderMasterService.paid(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}",refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}",refundResponse);
        return refundResponse;
    }
}
