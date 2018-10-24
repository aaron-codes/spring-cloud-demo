/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.djdg.pos.common.exception;


import com.djdg.pos.common.api.CommonErrorCode;
import com.djdg.pos.common.api.ErrorCode;

public class AppBusinessException extends BaseException {

    private static final ErrorCode DEFAULT_CODE = CommonErrorCode.INTERNAL_ERROR;

    private int code = DEFAULT_CODE.getCode();
    private int httpStatus = DEFAULT_CODE.getStatus();

    public AppBusinessException(int httpStatus, int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public AppBusinessException(String message) {
        super(message);
    }

    /**
     * @param errorCode 状态码, 这个字段会在错误信息里返回给客户端.
     * @param message
     */
    public AppBusinessException(ErrorCode errorCode, String message) {
        this(errorCode.getStatus(), errorCode.getCode(), message);
    }

    public AppBusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public int getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
