package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderFrom2OrderDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderFrom;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderMasterService orderMasterService;

    /**
     *  创建订单
     * @param orderFrom
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderFrom orderFrom, BindingResult bindingResult){
        //判断OrderFrom字段是否有错
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确，orderFrom={}",orderFrom);
            throw new SellException(bindingResult.getFieldError().getDefaultMessage(),ResultEnum.PARAM_ERROR.getCode());
        }
        //转换成orderDTO
        OrderDTO orderDTO = OrderFrom2OrderDTO.convert(orderFrom);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailEntityList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        //创建订单
        OrderDTO creatResult = orderMasterService.create(orderDTO);

        //要传回前台的数据
        Map<String,String> map = new HashMap<>();
        map.put("orderId",creatResult.getOrderId());

        return   ResultVoUtil.success(map);
    }
}
