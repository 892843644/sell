package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code,@RequestParam("state")String state){
        log.info("进去auth方法。。。");
        log.info("code={}",code);
        log.info("state={}",state);
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7e43b0ef828ebb9e&secret=07890f01882b23cbecd99264f6883202&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String jsonStr = restTemplate.getForObject(url, String.class);
        log.info("josnStr={}",jsonStr);
    }
}
