package com.xujie.springlcoud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xujie.springcloud.entities.CommonResult;
import com.xujie.springcloud.entities.Payment;
import com.xujie.springlcoud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private PaymentService paymentService;

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")
//    @SentinelResource(value = "fallback",blockHandler = "handlerFallback")
    @SentinelResource(value = "fallback",blockHandler = "blockHandler",fallback = "handlerFallback",
        exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(
                SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常...");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }
        return result;
    }

    public static CommonResult handlerFallback(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return new CommonResult(444, "fallback 调用 " +
                e.getMessage(), payment);
    }

    public static CommonResult blockHandler(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return new CommonResult(445, "blockHandler-sentinel限流，无此流水,blockExcption " +
                e.getMessage(), payment);
    }

     @GetMapping(value = "/consumer/paymentSQL/{id}")
     public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
         CommonResult<Payment> result = paymentService.paymentSQL(id);
         return result;
     }

}
