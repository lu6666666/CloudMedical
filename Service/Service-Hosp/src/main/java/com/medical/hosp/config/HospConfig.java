package com.medical.hosp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luo.X
 * @Description
 * @CreateTime 2022-09-06 10:35
 * @Version 1.0
 */
@Configuration
@MapperScan("com.medical.hosp.mapper")
public class HospConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor getPaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
