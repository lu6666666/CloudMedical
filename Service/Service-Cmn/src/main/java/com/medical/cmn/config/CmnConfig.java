package com.medical.cmn.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luo.X
 * @Description 配置类
 * @CreateTime 2022-09-08 9:36
 * @Version 1.0
 */
@Configuration
@MapperScan("com.medical.cmn.mapper")
public class CmnConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor getPaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
