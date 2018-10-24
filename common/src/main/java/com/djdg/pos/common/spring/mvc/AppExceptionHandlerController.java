/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.djdg.pos.common.spring.mvc;

import com.google.common.base.Joiner;

import com.djdg.pos.common.api.CommonErrorCode;
import com.djdg.pos.common.api.ErrorCode;
import com.djdg.pos.common.api.Result;
import com.djdg.pos.common.exception.AppBusinessException;
import com.djdg.pos.common.exception.RemoteCallException;
import com.djdg.pos.common.util.JsonUtils;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class AppExceptionHandlerController extends ResponseEntityExceptionHandler {

    protected Logger logger = LoggerFactory.getLogger(AppExceptionHandlerController.class);


    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = ex.getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, errorCode.getMessage());
        return createResponseEntity(errorCode, request.getDescription(false), errorMessage);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, errorCode.getMessage());
        return createResponseEntity(errorCode, request.getDescription(false), errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {

        logger.error("spring mvc 异常: " + ex.getMessage(), ex);
        ErrorCode errorCode = CommonErrorCode.fromHttpStatus(status.value());
        return createResponseEntity(errorCode, request.getDescription(false), errorCode.getMessage());
    }

    @ExceptionHandler(value = {ServiceUnavailableException.class, RemoteCallException.class})
    public ResponseEntity<Object> handleRemoteCallException(HttpServletRequest request, AppBusinessException e) {

        logger.error(e.getMessage(), e);
        return createResponseEntity(e.getCode(), e.getHttpStatus(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = HystrixTimeoutException.class)
    public ResponseEntity<Object> handleHystrixTimeoutException(HttpServletRequest request, HystrixTimeoutException e) {

        logger.error(e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.GATEWAY_TIMEOUT;
        return createResponseEntity(errorCode, request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = HystrixRuntimeException.class)
    public ResponseEntity<Object> handleHystrixRuntimeException(HttpServletRequest request, HystrixRuntimeException e) {

        logger.error("Hystrix Command 运行报错: " + e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_ERROR;
        return createResponseEntity(errorCode, request.getRequestURI(), e.getMessage());
    }



    @ExceptionHandler(value = AppBusinessException.class)
    public ResponseEntity<Object> handleAppBusinessException(HttpServletRequest request, AppBusinessException e) {

        //业务异常
        return createResponseEntity(e.getCode(), e.getHttpStatus(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception e) {

        logger.error("服务器发生错误: " + e.getMessage(), e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_ERROR;
        return createResponseEntity(errorCode, request.getRequestURI(), errorCode.getMessage());

    }


    private ResponseEntity<Object> createResponseEntity(ErrorCode errorCode, String requestUri, String message) {
        return createResponseEntity(errorCode.getStatus(), errorCode.getCode(), requestUri, message);
    }

    private ResponseEntity<Object> createResponseEntity(int httpStatus, Integer code, String requestUri, String message) {
        Result error = new Result(code, message, requestUri);
        String json = JsonUtils.object2Json(error);

        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(json);

    }


    private String extractErrorMessageFromObjectErrors(List<ObjectError> allErrors, String defaultMessage) {
        if(allErrors == null || allErrors.isEmpty()) {
            return defaultMessage;
        } else {
            List<String> errorMessages = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Joiner.on(",").skipNulls().join(errorMessages);
        }
    }
}