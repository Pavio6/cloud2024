package com.jlf.cloud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档地址
 * <a href="http://localhost:8001/doc.html">...</a>
 */
@Configuration
public class Knife4jConfiguration {

    @Bean
    public OpenAPI docsOpenAPI() {

        return new OpenAPI().info(
                new Info()
                        .title("cloud2024")  // API标题
                        .version("1.0") // 版本
                        .description("通用设计rest"));  // API描述
    }

    @Bean
    public GroupedOpenApi PayApi() {
        return GroupedOpenApi.builder().group("支付微服务模块").
                pathsToMatch(
                        "/pay/**"
                ).
                build();
    }
    @Bean
    public GroupedOpenApi OtherApi() {
        return GroupedOpenApi.builder().group("其他微服务模块").
                pathsToMatch(
                        "/other/**",
                        "/others"
                ).
                build();
    }
}
