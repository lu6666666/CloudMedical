package com.medical.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Luo.X
 * @Description 启动类
 * @CreateTime 2022-09-20 11:01
 * @Version 1.0
 */
// exclude = DataSourceAutoConfiguration.class代表取消数据源自动配置（连接数据库）
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 扫描swagger规则
@ComponentScan(basePackages = {"com.medical"})
// 注册中心
@EnableDiscoveryClient
// 开启Feign远程调用(Feign会自动引入@EnableCircuitBreaker(熔断))
@EnableFeignClients(basePackages = {"com.medical"})
public class OSSAppilcation {
    public static void main(String[] args) {
        SpringApplication.run(OSSAppilcation.class,args);
    }
}
