package com.medical.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Luo.X
 * @Description 启动类
 * @CreateTime 2022-09-04 16:27
 * @Version 1.0
 */
@SpringBootApplication
// 扫描swagger规则
@ComponentScan(basePackages = {"com.medical"})
// 注册中心
@EnableDiscoveryClient
// 开启Feign远程调用(Feign会自动引入@EnableCircuitBreaker(熔断))
@EnableFeignClients(basePackages = {"com.medical"})
public class HospApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospApplication.class,args);
    }
}
