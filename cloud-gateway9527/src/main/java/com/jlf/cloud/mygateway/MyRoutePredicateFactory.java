package com.jlf.cloud.mygateway;


import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义配置会员等级 user Type
 * 按照钻/金/银 和yml配置的会员等级 以适配是否可以访问
 */
//@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public MyRoutePredicateFactory() {
        super(Config.class);
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }
    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return serverWebExchange -> {
            // 检查request的参数里面 是否存在userType
            String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
            // 参数不存在 返回false
            if (userType == null) {
                return false;
            }
            // 参数存在 和config中的数据进行比较
            return userType.equalsIgnoreCase(config.getUserType());
        };
    }
    public static class Config {
        @NotNull
        private String userType;
        public String getUserType() {
            return userType;
        }

    }
}
