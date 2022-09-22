package com.medical.verify.model;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * @author Luo.X
 * @Description 邮箱实体类
 * @CreateTime 2022-09-18 15:10
 * @Version 1.0
 */
public class Email implements InitializingBean {

    @Value("${email.email_address}")
    private String email;

    @Value("${email.email_key}")
    private String email_key;
    /**
     * 163邮箱账号
     */
    public static String EMAIL;
    /**
     * 163邮箱密码（秘钥）
     */
    public static String EMAIL_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        EMAIL = email;
        EMAIL_KEY = email_key;
    }
}
