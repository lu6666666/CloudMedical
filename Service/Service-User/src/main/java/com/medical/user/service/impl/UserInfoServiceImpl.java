package com.medical.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.exception.customizeException;
import com.medical.helper.JwtHelper;
import com.medical.model.UserInfo;
import com.medical.result.ResultCodeEnum;
import com.medical.user.mapper.UserInfoMapper;
import com.medical.user.service.UserInfoService;
import com.medical.user.utils.WxCodeUtils;
import com.medical.vo.LoginVo;
import com.medical.vo.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 用户信息接口实现类
 * @CreateTime 2022-09-18 10:24
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Map<String, Object> loginPhoneService(LoginVo loginVo) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(loginVo.getPhone()) || StringUtils.isEmpty(loginVo.getCode())) {
            // 当参数有一个没有传递则返回异常
            throw new customizeException(ResultCodeEnum.PARAM_ERROR);
        }
        // 获取前台传递过来的手机号和验证码
        wrapper.eq("phone", loginVo.getPhone());
        // 拿着数据去数据库验证账号是否存在
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);

        // 获取redis中的验证码
        // 获取的邮箱验证码
        // String code = (String) redisTemplate.opsForValue().get("verification_code");
        // 获取的手机短信验证码
        String code = redisTemplate.opsForValue().get(loginVo.getPhone());
        if (null == userInfo) {
            // 没有被禁用验证验证码是否正确
            if (!loginVo.getCode().equals(code)) {
                // 验证码错误
                throw new customizeException(ResultCodeEnum.CODE_ERROR);
            }
            Date date = new Date();
            // 账号不存在验证码正确则先注册再登录
            UserInfo user = new UserInfo();
            user.setStatus(1);
            user.setNickName(loginVo.getPhone());
            user.setName(loginVo.getPhone());
            user.setPhone(loginVo.getPhone());
            user.setCreateTime(date);
            user.setUpdateTime(date);
            // 保存
            userInfoMapper.insert(user);
            // 把刚保存出来的数据再查询出来
            UserInfo info = userInfoMapper.selectOne(wrapper);
            ;
            map.put("name", loginVo.getPhone());
            // 根据用户id和名称生成token
            map.put("token", JwtHelper.createToken(info.getId(), info.getNickName()));
        } else {
            // 账号存在
            // 验证账号是否被禁用
            if (userInfo.getStatus() == 0) {
                throw new customizeException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
            }
            // 没有被禁用验证验证码是否正确
            if (!loginVo.getCode().equals(code)) {
                // 验证码错误
                throw new customizeException(ResultCodeEnum.CODE_ERROR);
            }
            // 正确把name和token存入map
            map.put("name", userInfo.getNickName());
            map.put("token", JwtHelper.createToken(userInfo.getId(), userInfo.getNickName()));
        }
        return map;
    }

    /**
     * 微信登录返回二维码生成主要参数
     *
     * @return
     */
    @Override
    public Map<String, Object> wxCodeInfoService() throws UnsupportedEncodingException {
        System.out.println("参数：" + WxCodeUtils.WX_OPEN_REDIRECT_URL);
        String redirectURl = URLEncoder.encode(WxCodeUtils.WX_OPEN_REDIRECT_URL, "UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("appid", WxCodeUtils.WX_OPEN_APP_ID);
        map.put("redirectURi", redirectURl);
        map.put("scope", "snsapi_login");
        map.put("state", System.currentTimeMillis() + "");
        return map;
    }

    /**
     * 用户实名认证
     * @param userAuthVo
     */
    @Override
    public void userCertificationService(UserAuthVo userAuthVo) {
        System.out.println(userAuthVo);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userAuthVo.getUserId());
        userInfo.setName(userAuthVo.getName());
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(2);
        System.out.println(userInfo);
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    @Override
    public Map<String, Object> parseTokenService(String token) {
        Map<String, Object> map = new HashMap<>();
        Long userId = JwtHelper.getUserId(token);
        String userName = JwtHelper.getUserName(token);
        map.put("id",userId);
        map.put("userName",userName);
        return map;
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Override
    public UserInfo getUserInfoService(Long id) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        return userInfo;
    }
}
