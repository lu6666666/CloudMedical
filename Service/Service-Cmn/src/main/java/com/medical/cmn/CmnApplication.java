package com.medical.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Luo.X
 * @Description 启动类
 * @CreateTime 2022-09-08 9:32
 * @Version 1.0
 */
@SpringBootApplication
// 扫描swagger规则
@ComponentScan(basePackages = {"com.medical"})
public class CmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmnApplication.class,args);
    }
}
