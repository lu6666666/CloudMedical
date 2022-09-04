package com.medical;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Luo.X
 * @Description 启动类
 * @CreateTime 2022-09-04 16:27
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.medical.mapper")
public class HospApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospApplication.class,args);
    }
}
