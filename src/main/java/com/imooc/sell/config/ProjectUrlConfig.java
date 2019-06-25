package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author ：cjy
 * @description ：项目路径配置
 * @CreateTime ：Created in 2019/6/24 22:05
 */
@Data
@ConfigurationProperties(prefix = "project-url")
@Component
public class ProjectUrlConfig {

    /** 微信公众平台授权url*/
    public String wechatMpAuthorize;

    /** 微信开放平台授权url*/
    public String wechatOpenAuthorize;

    /** 点餐系统*/
    public String sell;

}
