package com.jlf.cloud.controller;

import com.jlf.cloud.apis.PayFeignAPI;
import com.jlf.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderGateWayController {
    @Resource
    private PayFeignAPI payFeignAPI;

    @GetMapping(value = "/feign/pay/gateway/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id) {
        return payFeignAPI.getById(id);
    }

    @GetMapping(value = "/feign/pay/gateway/info")
    public ResultData<String> getGatewayInfo() {
        return payFeignAPI.getGatewayInfo();
    }
}


 