package com.medical.verify.service;

import com.medical.verify.model.Email;

/**
 * @author Luo.X
 * @Description 登录邮箱验证 接口
 * @CreateTime 2022-09-18 15:08
 * @Version 1.0
 */
public interface CodeService {

    /**
     * 给邮箱发送验证码
     * @param email
     * @return
     */
    String sendEmail(String email);

    /**
     * 阿里云发送短信验证码
     * @param phone
     * @return
     */
    String getAliCode(String phone);
}
