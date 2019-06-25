package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.utils.StringUitl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/6/25 11:19
 */
@RestController
@RequestMapping("/sms")
@Slf4j
public class SendSmsController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sendsms")
    public String sendsms(String phone){
        String randomCode = StringUitl.fourRandom();
        //单发短信API
        String url = "https://open.ucpaas.com/ol/sms/sendsms";
        JSONObject jsonObject=new JSONObject();
        //基础配置，在开发平台认证后获取
        jsonObject.put("sid","79b8cdabd6bc5b09245ae6e1d6b5a771");
        jsonObject.put("token","cb2f124e0d05c6cc40e6de6a6da81f91");
        jsonObject.put("appid","b3b21dc2a6984c0fab1ffefdbe5ec4e9");
        //模板ID，在开发平台创建模板对应的模板ID
        jsonObject.put("templateid", "478836");
        //模板对应的参数，参数之间拼接用逗号作为间隔符
        jsonObject.put("param",randomCode+",60");
        //要发送的手机号
        jsonObject.put("mobile", phone);
        //用户透传ID，随状态报告返回,可以不填写
        jsonObject.put("uid","");
        String json = JSONObject.toJSONString(jsonObject);
        //使用restTemplate进行访问远程服务
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        //把验证码存到redis
        redisTemplate.opsForValue().set(phone+"_randomCode",randomCode,60, TimeUnit.SECONDS);
        String result = restTemplate.postForObject(url, httpEntity, String.class);
        log.info("【sendsms】  result={}",result);
        return result;
    }

}
