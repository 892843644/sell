package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    public ResultVO<Map<String,String>> create(@Valid  OrderFrom orderFrom, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SellException()
        }
    }
}
