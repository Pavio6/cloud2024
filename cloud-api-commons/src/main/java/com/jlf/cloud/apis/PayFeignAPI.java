package com.jlf.cloud.apis;

import com.jlf.cloud.entities.PayDTO;
import com.jlf.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// FeignClient 让 Spring 应用通过 Feign 进行远程 HTTP 调用
// value 指定远程服务的名称(通常是服务注册到 Consul 等服务注册中心时的服务名称)
@FeignClient(value = "cloud-payment-service")
public interface PayFeignAPI {
    /**
     * 新增一条支付相关的流水记录
     */
    @PostMapping(value = "/pay/add")
    ResultData addPay(@RequestBody PayDTO payDTO);

    /**
     * 按照主键记录查询支付流水信息
     */
    @GetMapping(value = "/pay/get/{id}")
    ResultData getPayInfo(@PathVariable("id") Integer id);

    /**
     * OpenFeign 天然支持负载均衡
     */
    @GetMapping(value = "/pay/get/info")
    String mylb();
}
