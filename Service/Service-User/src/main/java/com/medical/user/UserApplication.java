package com.medical.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Luo.X
 * @Description 启动类
 * @CreateTime 2022-09-18 10:04
 * @Version 1.0
 */
@SpringBootApplication
// 扫描swagger规则
@ComponentScan(basePackages = {"com.medical"})
// 注册中心
@EnableDiscoveryClient
// 开启Feign远程调用(Feign会自动引入@EnableCircuitBreaker(熔断))
@EnableFeignClients(basePackages = {"com.medical"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
