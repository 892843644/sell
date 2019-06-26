package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    /**
     * 公众号id
     */
    private String mpAppId;

    /**
     * 公众号Secret
     */
    private String mpAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书
     */
    private String keyPath;
    /**
     * 微信异步通知地址
     */
    private String notifyUrl;

    /**
     * 微信消息推送模板id
     */
    private Map<String,String> templateId;
}
