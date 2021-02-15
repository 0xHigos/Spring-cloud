package com.xujie.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "---- PaymentFallbackService fall back  paymentInfo_OK 0__0";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return " --- PaymentFallbackService fall back  paymentInfo_Timeout  0__0";
    }
}
