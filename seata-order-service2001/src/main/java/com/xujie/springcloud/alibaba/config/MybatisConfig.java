package com.xujie.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xujie.springcloud.alibaba.dao")
public class MybatisConfig {

}
