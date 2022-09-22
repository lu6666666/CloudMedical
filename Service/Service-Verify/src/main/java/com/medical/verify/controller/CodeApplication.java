package com.medical.verify.controller;

import com.medical.verify.service.CodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luo.X
 * @Description 发送验证码 controller
 * @CreateTime 2022-09-18 15:07
 * @Version 1.0
 */
@Api(tags = "发送验证码")
@RestController
@RequestMapping("/site/code")
public class CodeApplication {

    @Autowired
    private CodeService codeService;

    /**
     * 发送验证码并获取
     *
     * @param email
     * @return
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping("/getCode/{email}")
    public String getCode(@PathVariable String email) {
        return codeService.sendEmail(email);
    }

    /**
     * 阿里云发送短信验证码
     */
    @ApiOperation(value = "阿里云发送短信验证码")
    @GetMapping("/getAliCode/{phone}")
    public void getAliCode(@PathVariable String phone) {
        codeService.getAliCode(phone);
    }

    /**
     * 微信登录
     */

}
