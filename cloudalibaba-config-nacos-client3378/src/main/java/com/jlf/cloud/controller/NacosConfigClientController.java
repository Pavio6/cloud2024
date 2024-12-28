package com.jlf.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nacos 全局配置 动态刷新
 * Namespace-Group-DataId 三元组 待学习
 */
@RestController
@RefreshScope
public class NacosConfigClientController {
    @Value("${config.info}")
    private String configInfo;
    @GetMapping("/config/info")
    public String configInfo() {
        return configInfo;
    }
}
