package com.medical.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medical.model.UserInfo;
import com.medical.vo.LoginVo;
import com.medical.vo.UserAuthVo;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 用户信息接口
 * @CreateTime 2022-09-18 10:23
 * @Version 1.0
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户手机号登陆
     * @param loginVo
     * @return
     */
    Map<String, Object> loginPhoneService(LoginVo loginVo);

    /**
     * 微信登录返回二维码生成主要参数
     * @return
     */
    Map<String, Object> wxCodeInfoService() throws UnsupportedEncodingException;

    /**
     * 用户实名认证
     * @param userAuthVo
     */
    void userCertificationService(UserAuthVo userAuthVo);

    /**
     * 解析token
     * @param token
     * @return
     */
    Map<String, Object> parseTokenService(String token);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserInfo getUserInfoService(Long id);
}
