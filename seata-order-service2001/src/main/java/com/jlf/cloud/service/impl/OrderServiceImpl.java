package com.jlf.cloud.service.impl;

import com.jlf.cloud.apis.AccountFeignApi;
import com.jlf.cloud.apis.StorageFeignApi;
import com.jlf.cloud.entities.Order;
import com.jlf.cloud.mapper.OrderMapper;
import com.jlf.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    // 订单微服务通过OpenFeign调用库存微服务
    @Resource
    private StorageFeignApi storageFeignApi;
    // 订单微服务通过OpenFeign调用账户微服务
    @Resource
    private AccountFeignApi accountFeignApi;

    @Override
    // 启动全局事务 确保涉及的所有微服务都能够在全局事务中进行操作 保证事务的原子性
    @GlobalTransactional(name = "jlf-create-order", rollbackFor = Exception.class) // AT模式下
    public void create(Order order) {
        // xid 全局事务id的检查
        String xid = RootContext.getXID();
        // 1. 新建订单
        log.info("-----开始新建订单" + "\t" + "xid: " + xid);
        // 订单新建时默认初始订单状态为零
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);
        Order orderFromDB;
        if (result > 0) {
            orderFromDB = orderMapper.selectOne(order);
            log.info("-----新建订单成功, " + "\t" + "orderFromDB " + orderFromDB);
            System.out.println();
            //2. 扣减库存
            log.info("-------> 订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("-------> 订单微服务结束调用Storage库存，做扣减完成");
            System.out.println();
            //3. 扣减账号余额
            log.info("-------> 订单微服务开始调用Account账号，做扣减money");
            accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            log.info("-------> 订单微服务结束调用Account账号，做扣减完成");
            System.out.println();
            //4. 修改订单状态
            //订单状态status：0：创建中；1：已完结
            log.info("-------> 修改订单状态");
            orderFromDB.setStatus(1);

            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId", orderFromDB.getUserId());
            criteria.andEqualTo("status", 0);

            int updateResult = orderMapper.updateByExampleSelective(orderFromDB, whereCondition);

            log.info("-------> 修改订单状态完成" + "\t" + updateResult);
            log.info("-------> orderFromDB info: " + orderFromDB);
        }
        System.out.println();
        log.info("-----结束新建订单" + "\t" + "xid: " + xid);
    }
}
