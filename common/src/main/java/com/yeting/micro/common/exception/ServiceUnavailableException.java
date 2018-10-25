/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.yeting.micro.common.exception;

import com.yeting.micro.common.api.CommonErrorCode;
import com.yeting.micro.common.api.ErrorCode;


public class ServiceUnavailableException extends AppBusinessException {

    private static final ErrorCode ERROR_CODE = CommonErrorCode.SERVICE_UNAVAILABLE;

    public ServiceUnavailableException(String message) {
        super(ERROR_CODE.getStatus(), ERROR_CODE.getCode(), " 远程服务不可用: " + message);
    }

}
