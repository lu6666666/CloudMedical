package com.medical.user.controller;

import com.medical.model.UserInfo;
import com.medical.result.Result;
import com.medical.user.service.UserInfoService;
import com.medical.vo.LoginVo;
import com.medical.vo.UserAuthVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 用户信息 controller
 * @CreateTime 2022-09-18 10:25
 * @Version 1.0
 */

@Api(tags = "用户信息")
@RestController
@RequestMapping("/site/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户手机号登陆
     */
    @ApiOperation(value = "用户手机号登陆")
    @PostMapping("/loginPhone")
    public Result loginPhone(@RequestBody LoginVo loginVo){
        Map<String,Object> map = userInfoService.loginPhoneService(loginVo);
        return Result.ok(map);
    }

    /**
     * 解析token
     */
    @ApiOperation(value = "解析token")
    @GetMapping("/parseToken/{token}")
    public Result parseToken(@PathVariable String token){
        Map<String,Object> map = userInfoService.parseTokenService(token);
        return Result.ok(map);
    }

    /**
     * 根据id查询用户信息
     */
    @ApiOperation(value = "根据id查询用户信息")
    @GetMapping("/getUserInfo/{id}")
    public Result getUserInfo(@PathVariable Long id){
        UserInfo userInfo = userInfoService.getUserInfoService(id);
        return Result.ok(userInfo);
    }

    /**
     * 用户实名认证 (实际就是修改用户的信息把用户的身份证姓名之类的存入数据库)
     */
    @ApiOperation(value = "用户实名认证")
    @PostMapping("/userCertification")
    public Result userCertification(@RequestBody UserAuthVo userAuthVo){
        userInfoService.userCertificationService(userAuthVo);
        return Result.ok();
    }
}
