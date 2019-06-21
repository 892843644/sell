package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.rest.type.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.rmi.MarshalledObject;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private PayService payService;
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //1.查询订单
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        //没有微信支付接口 无法完成 跳过
       // PayResponse payResponse = payService.create(orderDTO);
       // map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        //修改订单支付状态
        orderMasterService.paid(orderDTO);
        return new ModelAndView("pay/create",map);
    }

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public void notify(@RequestBody String notifyData){

    }
}
