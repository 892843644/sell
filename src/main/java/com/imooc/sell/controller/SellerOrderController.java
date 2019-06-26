package com.imooc.sell.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.sell.converter.OrderMaster2OrderDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderMasterEntity;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Author ：cjy
 * @Description ：卖家端订单
 * @CreateTime ：Created in 2019/6/20 18:14
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 订单列表
     * @param page 第几页 从第一页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){

        IPage<OrderMasterEntity> orderMasterEntityPage=new Page<>(page,size);
        QueryWrapper<OrderMasterEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        orderMasterEntityPage = orderMasterService.page(orderMasterEntityPage,queryWrapper);
        IPage<OrderDTO> orderDTOPage = new Page<>();
        BeanUtils.copyProperties(orderMasterEntityPage,orderDTOPage);

        //得到orderMasterEntityPage的查询记录
        List<OrderMasterEntity> orderMasterEntityList = orderMasterEntityPage.getRecords();

        //转换成 List<OrderDTO>
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterEntityList);

        //将转换完的set进orderDTOPage对象
        orderDTOPage.setRecords(orderDTOList);
        map.put("orderDTOPage",orderDTOPage);
        map.put("pages",orderDTOPage.getPages());
        map.put("size",size);
        return new ModelAndView("order/list",map);

    }

    /**
     * 取消订单
     * @param orderId 订单id
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        //查询订单
        try {
            OrderDTO orderDTO = orderMasterService.findOne(orderId);
            //取消订单
            orderDTO = orderMasterService.cancel(orderDTO);
        }catch (SellException e){
            log.error("【卖家端取消订单】 发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");


        return new ModelAndView("common/success",map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        OrderDTO orderDTO = null;
        try {
            orderDTO=  orderMasterService.findOne(orderId);
        }catch (SellException e){
            log.error("【卖家端查询订单详情】 发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);

    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        try {
            OrderDTO orderDTO=  orderMasterService.findOne(orderId);
            orderMasterService.finish(orderDTO);
        }catch (SellException e){
            log.error("【卖家端完结订单】 发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
