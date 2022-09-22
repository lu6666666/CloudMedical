package com.medical.user.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Luo.X
 * @Description 微信登录开发者信息
 * @CreateTime 2022-09-20 9:26
 * @Version 1.0
 */
@Component
public class WxCodeUtils implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String appID;

    @Value("${wx.open.app_secret}")
    private String appSecret;

    // 重定向地址
    @Value("${wx.open.redirect_uri}")
    private String redirectUri;

    @Value("${site.baseUrl}")
    private String baseUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;
    public static String SITE_BASE_URL;


    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appID;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUri;
        SITE_BASE_URL =  baseUrl;
    }
}
