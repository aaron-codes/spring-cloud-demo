/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.yeting.micro.common.api;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer SUCC = 0;
    public static final Integer FAILED = 1;

    private Integer statusCode;
    private String msg;
    private T data;

    public Result() {

    }

    public Result(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public Result(Integer statusCode, String msg, T data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public static Result<Object> SuccResult() {
        return new Result<Object>(SUCC, "成功");
    }

    public static Result<Object> SuccResult(String msg) {
        return new Result<Object>(SUCC, msg);
    }
    public static <T>Result<T> SuccResult(String msg,T t) {
        return new Result<>(SUCC, msg,t);
    }

    public static Result FailedResult() {
        return new Result<>(FAILED, "失败");
    }

    public static Result<Void> FailedResult(String msg) {
        return new Result<>(FAILED, msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;

    }

    public Boolean isSuccess() {
        return SUCC.equals(statusCode);
    }

}
