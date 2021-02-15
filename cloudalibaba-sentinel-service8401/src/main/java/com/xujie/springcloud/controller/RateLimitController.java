package com.xujie.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xujie.springcloud.CustomerBlockHander;
import com.xujie.springcloud.entities.CommonResult;
import com.xujie.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200,
                "按资源名称限流测试ok",
                new Payment(2020L, "serial001"));
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl",blockHandler = "handleException")
    public CommonResult byUrl() {
        return new CommonResult(200,
                "按url限流测试ok",
                new Payment(2020L, "serial002"));
    }

    public CommonResult handleException(BlockException blockException) {
        return new CommonResult(444,
                blockException.getClass().getCanonicalName()
                        + "\t 服务不可用");
    }

    //CustomerBlockController
    @GetMapping("/rateLimit/CustomerBlockHandler")
    @SentinelResource(value = "CustomerBlockHandler",
            blockHandlerClass = CustomerBlockHander.class,
            blockHandler = "handlerException")
    public CommonResult CustomerBlockHandler() {
        return new CommonResult(200,
                "按客户自定义限流测试ok",
                new Payment(2020L, "serial003"));
    }


}
