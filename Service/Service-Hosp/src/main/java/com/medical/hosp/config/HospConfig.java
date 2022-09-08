package com.medical.hosp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

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

    /**
     * 方式二：采用objectMapper注入
     * 解决前台由于id过长传递过来的数据不一致问题（会将所有long类型转换为string类型）
     */
    /*@Bean
    public ObjectMapper objectMapper (Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule simpleModule = new SimpleModule();
        // 直接将所有的Long类型转换为String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }*/
}
