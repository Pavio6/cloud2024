package com.jlf.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.jlf.cloud.apis.PayFeignAPI;
import com.jlf.cloud.entities.PayDTO;
import com.jlf.cloud.resp.ResultData;
import com.jlf.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Resource
    private PayFeignAPI payFeignAPI;

    @PostMapping(value = "/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO) {
        ResultData resultData = payFeignAPI.addPay(payDTO);
        return resultData;
    }

    /**
     * 测试 OpenFeign超时控制
     */
    @GetMapping(value = "/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        ResultData resultData = null;
        try {
            System.out.println("调用开始-----" + DateUtil.now());
            resultData = payFeignAPI.getPayInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-----" + DateUtil.now());
            ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return  resultData;
    }
    @GetMapping(value = "/feign/pay/mylb")
    public String mylb() {
        return payFeignAPI.mylb();
    }

}
