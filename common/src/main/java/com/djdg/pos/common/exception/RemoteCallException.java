/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.djdg.pos.common.exception;


import com.djdg.pos.common.api.Result;

/**
 * hystrix 会忽略这个异常, 不会触发熔断
 *
 */
public class RemoteCallException extends AppBusinessException {

    private Result originResult;

    public RemoteCallException(int httpStatus, Result result) {
        super(httpStatus, result.getStatusCode(), "调用远程服务异常, cause: " + result.getMsg());
        this.originResult = result;
    }

    public Result getOriginResult() {
        return originResult;
    }
}
