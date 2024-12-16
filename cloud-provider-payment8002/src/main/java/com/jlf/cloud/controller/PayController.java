package com.jlf.cloud.controller;

import com.jlf.cloud.entities.Pay;
import com.jlf.cloud.entities.PayDTO;
import com.jlf.cloud.resp.ResultData;
import com.jlf.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 状态码分类及描述
 * 100 - 199：信息 服务器收到请求 需要请求者继续执行操作
 * 200 - 299：成功 操作被成功接收并处理
 * 300 - 399：重定向 需要进一步的操作以完成请求
 * 400 - 499：客户端错误 请求包含语法错误或无法完成请求
 * 500 - 599：服务器错误 服务器在处理请求的过程中发生了错误
 */
@RestController
@Slf4j
@Tag(name = "支付微服务模块",description = "支付CRUD")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping(value = "/pay/add")
    @Operation(summary = "新增",description = "新增支付流水方法,json串做参数")
    public ResultData<String> addPay(@RequestBody Pay pay) {

        System.out.println(pay.toString());
        int result = payService.add(pay);
        return ResultData.success("成功插入记录, 返回值: " + result);
    }
    @DeleteMapping(value = "/pay/del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id) {
        return ResultData.success(payService.delete(id));
    }
    @PutMapping(value = "/pay/update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);
        int result = payService.update(pay);
        return ResultData.success("成功修改记录, 返回值" + result);
    }
    @GetMapping(value = "/pay/get/{id}")
    @Operation(summary = "按照ID查流水",description = "查询支付流水方法")
    public ResultData<Pay> getById(@PathVariable("id") Integer id) {
        // 这里会触发全局异常处理类中的exception方法
        if(id == -4) throw new RuntimeException("id不能为负数");
        return ResultData.success(payService.getById(id));
    }
    @GetMapping(value = "/pay/get")
    @Operation(summary = "查询全部流水", description = "查询全部支付流水方法")
    public ResultData<List<Pay>> getAll() {
        return ResultData.success(payService.getAll());
    }

    @Value("${server.port}")
    private String port;
    @GetMapping(value = "/pay/get/info")
    public String getInfoByConsul(@Value("${jlf.info}") String jlfInfo) {
        return "jlfInfo: " + jlfInfo + "\t" + "port: " + port;
    }

}
