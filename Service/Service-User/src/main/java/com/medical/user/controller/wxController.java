package com.medical.user.controller;

import com.medical.result.Result;
import com.medical.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Luo.X
 * @Description
 * @CreateTime 2022-09-20 10:13
 * @Version 1.0
 */
@Api(tags = "微信登录")
@Controller
@RequestMapping("/site/wx")
public class wxController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 微信登录返回二维码生成主要参数
     */
    @ApiOperation(value = "微信登录返回二维码生成主要参数")
    @GetMapping("/wxCodeInfo")
    @ResponseBody
    public Result wxCodeInfo(HttpSession session){
        Map<String,Object> map = null;
        try {
            map = userInfoService.wxCodeInfoService();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Result.fail();
        }
        return Result.ok(map);
    }
}
