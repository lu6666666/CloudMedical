package com.medical.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luo.X
 * @Description 配置类
 * @CreateTime 2022-09-18 10:27
 * @Version 1.0
 */
@Configuration
@MapperScan("com.medical.user.mapper")
public class UserConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor getPaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
