package com.jlf.cloud.mygateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AddResponseHeaderGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局Filter
 */
@Component //不要忘记
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {
    // 开始调用方法的时间
    public static final String BEGIN_VISIT_TIME = "begin_visit_time";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.先记录访问接口的开始时间
        exchange.getAttributes()
                .put(BEGIN_VISIT_TIME, System.currentTimeMillis());
        // 2.返回统计的各个结果
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (beginVisitTime != null) {
                log.info("访问接口主机:" + exchange.getRequest().getURI().getHost());
                log.info("访问接口端口:" + exchange.getRequest().getURI().getPort());
                log.info("访问接口URL:" + exchange.getRequest().getURI().getPort());
                log.info("访问接口URL后面的参数:" + exchange.getRequest().getURI().getRawQuery());
                log.info("访问接口时长:" + (System.currentTimeMillis() - beginVisitTime) + "毫秒");
            }
            System.out.println("----------------------------");
        }));
    }

    /**
     * 数字越小 优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}