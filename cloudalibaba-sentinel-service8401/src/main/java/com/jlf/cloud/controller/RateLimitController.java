package com.jlf.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j // @Slf4j 是 Lombok 提供的一个注解，它可以自动为类添加一个 org.slf4j.Logger 实例
public class RateLimitController {
    @GetMapping("/rateLimit/byUrl")
    public String byUrl() {
        return "按rest地址限流";
    }

    /**
     * 按SentinelResource资源名称限流 自定义限流返回
     */
    @GetMapping("/rateLimit/byResource")
    // blockHandler 它用于指定当某个方法被流量控制（如限流、降级等）触发时，应该调用的备用处理方法
    @SentinelResource(value = "byResourceSentinelResource", blockHandler = "handleException")
    public String byResource() {
        return "按资源名称SentinelResource限流测试OK";
    }

    public String handleException(BlockException exception) {
        return "服务不可用@SentinelResource启动" + "\t" + "o(╥﹏╥)o";
    }

    /**
     * 按SentinelResource资源名称限流 + 自定义限流返回 + 服务降级处理
     */
    @GetMapping("/rateLimit/doAction/{p1}")
    @SentinelResource(value = "doActionSentinelResource",
            blockHandler = "doActionBlockHandler", fallback = "doActionFallback")
    // blockHandler 主要针对sentinel配置后出现的违规情况处理
    // fallback 程序异常了JVM抛出的异常服务降级
    public String doAction(@PathVariable("p1") Integer p1) {
        if (p1 == 0) {
            throw new RuntimeException("p1等于零直接异常");
        }
        return "doAction";
    }

    public String doActionBlockHandler(@PathVariable("p1") Integer p1, BlockException e) {
        log.error("sentinel配置自定义限流了", e);
        return "sentinel配置自定义限流了";
    }

    public String doActionFallback(@PathVariable("p1") Integer p1, Throwable e) {
        log.error("程序逻辑异常了", e);
        return "程序逻辑异常了" + "\t" + e.getMessage();
    }

    /**
     * 热点参数限流
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }

    public String dealHandler_testHotKey(String p1, String p2, BlockException exception) {
        return "-----dealHandler_testHotKey";
    }
}
