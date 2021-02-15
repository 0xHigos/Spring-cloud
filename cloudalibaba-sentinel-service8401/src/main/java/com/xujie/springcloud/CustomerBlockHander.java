package com.xujie.springcloud;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xujie.springcloud.entities.CommonResult;

public class CustomerBlockHander {

    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(4444,
                "按客户自定义，global handlerException---01");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(4444,
                "按客户自定义，global handlerException---02");
    }

}
