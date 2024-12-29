package com.jlf.cloud.controller;

import com.jlf.cloud.entities.Order;
import com.jlf.cloud.resp.ResultData;
import com.jlf.cloud.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Seata AT模式
 * 通过利用数据库本地事务和 undo 日志  确保分布式系统中的各个微服务数据保持一致性
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     */
    @GetMapping("/order/create")
    public ResultData create(Order order) {
        orderService.create(order);
        return ResultData.success(order);
    }
}