package com.jlf.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign是一种声明式的HTTP客户端 用于微服务中进行服务之间的通信
 * 主要作用：简化HTTP请求的编写 只需要定义接口指定请求的URL和方法
 */
@Configuration
public class FeignConfig {
    /**
     * 配置Feign的重试机制
     */
    @Bean
    public Retryer feignRetryer() {
        // Feign 默认配置不走重试机制
         return Retryer.NEVER_RETRY;

        // period: 初始重试时间 毫秒
        // maxPeriod: 重试间隔的增量
        // maxAttempts: 最大重试次数
        // return new Retryer.Default(100, 1, 3);
    }

    /**
     * 配置Feign的日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
