package com.jlf.cloud.service;

import com.jlf.cloud.entities.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);

}