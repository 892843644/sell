package com.imooc.sell.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderFrom2OrderDTO;
import com.imooc.sell.converter.OrderMaster2OrderDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.entity.OrderMasterEntity;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderFrom;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderDetailService;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private BuyerService buyerService;
    
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

    /**
     *  查询分页订单列表
     * @param openid 微信openid
     * @param page 分页后的第几页 默认第一页
     * @param size 分页的每页数据条数 默认10条
     * @return 返回分页后内容
     */
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                   @RequestParam(value = "page",defaultValue = "1") Integer page,
                                   @RequestParam(value = "size",defaultValue = "10") Integer size){
        //判断openid是否为空
        if(StringUtils.isEmpty(openid)){
            log.info("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //实现分页
        QueryWrapper<OrderMasterEntity> queryWrapper = new QueryWrapper<>();
        IPage<OrderMasterEntity> orderMasterEntityPage=new Page<>(page,size);
        IPage<OrderDTO> orderDTOIPage=new Page<>();

        //构造查询语句
        queryWrapper.eq("buyer_openid",openid);
        orderMasterEntityPage= orderMasterService.page(orderMasterEntityPage, queryWrapper);
        //克隆
        BeanUtils.copyProperties(orderMasterEntityPage,orderDTOIPage);
        List<OrderMasterEntity> orderMasterEntityList = orderMasterEntityPage.getRecords();
        //调用方法转换
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterEntityList);
        //set
        orderDTOIPage.setRecords(orderDTOList);
        List<OrderDTO> records = orderDTOIPage.getRecords();
        log.info(orderDTOIPage.getRecords()+"");

        return  ResultVoUtil.success(records);
    }

    /**
     * 查询订单详情
     * @param openid 微信openid
     * @param orderId 订单id
     * @return ResultVO<OrderDTO>
     */
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){

        OrderDTO orderOne = buyerService.findOrderOne(openid, orderId);
        return  ResultVoUtil.success(orderOne);
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){

        buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success();
    }
}
