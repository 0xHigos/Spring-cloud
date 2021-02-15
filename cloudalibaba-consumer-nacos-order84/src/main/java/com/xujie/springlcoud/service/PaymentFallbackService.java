package com.xujie.springlcoud.service;

import com.xujie.springcloud.entities.CommonResult;
import com.xujie.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(4444,
                "服务降级返回，--PaymentFallbackService",
                new Payment(id, "errorPayment"));
    }
}
