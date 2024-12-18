package com.jlf.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 支付模块
 */
@EnableDiscoveryClient // 开启服务发现
@SpringBootApplication
@MapperScan("com.jlf.cloud.mapper") // import tk.mybatis.spring.annotation.MapperScan;
public class Main8001 {
    public static void main(String[] args) {
        SpringApplication.run(Main8001.class, args);
    }
}
