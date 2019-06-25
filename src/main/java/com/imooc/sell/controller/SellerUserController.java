package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.entity.SellerInfoEntity;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.SellerInfoService;
import com.imooc.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/6/24 21:13
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerInfoService sellerInfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @PostMapping("/login")
    public ModelAndView login(String phone,
                              String inputCode,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.phone去和数据库里的数据匹配
        QueryWrapper<SellerInfoEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        SellerInfoEntity sellerInfoEntity = sellerInfoService.getOne(queryWrapper);
        if(sellerInfoEntity==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        //判断验证码是否正确
        String  randomCode= redisTemplate.opsForValue().get(phone + "_randomCode");
        if(randomCode==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        if(!inputCode.equals(randomCode)){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        //2.设置token至redis
        String token=UUID.randomUUID().toString();
        Integer expire= RedisConstant.EXPIRE;//过期时间
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),phone,expire, TimeUnit.SECONDS);

        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1.从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
       if(cookie!=null){
           //2.清除redis
           redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
           //3.清除cookie
           CookieUtil.set(response,CookieConstant.TOKEN,null,0);
       }
       map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMsg());
       map.put("url","/sell/seller/order/list");
       return new ModelAndView("common/success",map);
    }
}
