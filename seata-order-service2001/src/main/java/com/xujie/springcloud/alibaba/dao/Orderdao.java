package com.xujie.springcloud.alibaba.dao;

import com.xujie.springcloud.alibaba.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Orderdao {
    //1.新建订单
    void create(Order order);

    //2.修改订单状态 从零改为1
    void update(@Param("userId") Long userId, @Param("status") Integer status);

}
