package com.medical.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Luo.X
 * @Description
 * @CreateTime 2022-09-20 14:41
 * @Version 1.0
 */
@Component
public class AliUtils implements InitializingBean {

    @Value("${Alibaba.open.endpoint}")
    private String endpoint;
    @Value("${Alibaba.open.accessKeyId}")
    private String accessKeyId;
    @Value("${Alibaba.open.accessKeySecret}")
    private String accessKeySecret;
    @Value("${Alibaba.open.bucketName}")
    private String bucketName;

    public static String ENDPOINT;
    public static String ACCESSKEY_ID;
    public static String ACCESSKEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ACCESSKEY_ID = accessKeyId;
        ACCESSKEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
    }
}
