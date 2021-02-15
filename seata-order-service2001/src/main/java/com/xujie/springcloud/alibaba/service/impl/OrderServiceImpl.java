package com.xujie.springcloud.alibaba.service.impl;

import com.xujie.springcloud.alibaba.dao.Orderdao;
import com.xujie.springcloud.alibaba.Order;
import com.xujie.springcloud.alibaba.service.AccountService;
import com.xujie.springcloud.alibaba.service.OrderService;
import com.xujie.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j

public class OrderServiceImpl implements OrderService {

    @Resource
    private Orderdao orderdao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "fsp_tx_group",rollbackFor = Exception.class)
    public void create(Order order) {
        //1.新建订单
        log.info("------>开始新建订单");
        orderdao.create(order);

        //2.扣减库存
        log.info("---->订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("---->订单微服务开始调用库存，做扣减end");


        //3.扣减账户
        log.info("---->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("---->订单微服务开始调用账户，做扣减end");

        //4.修改订单的状态 从零到1，1代表已经完成
        log.info("----> 修改订单状态开始");
        orderdao.update(order.getId(),1);
        log.info("----> 修改订单状态结束");

        log.info("---->下订单结束了~~");
    }
}
