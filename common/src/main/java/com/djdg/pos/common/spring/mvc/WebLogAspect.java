/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.djdg.pos.common.spring.mvc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/*
 * Created by Mr.OuYangX, 2017/5/18 下午10:56.
 */
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.djdg.pos.*.web..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        Enumeration<String> headerNames = request.getHeaderNames();
        logger.info("HEADER : ");
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement().toString();
            logger.info(name + " : " + request.getHeader(name));
        }
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        try {
            logger.info("REQUEST : " + JSONArray.toJSONString(joinPoint.getArgs()));
        } catch (Exception e) {
            logger.warn("REQUEST BODY PARSE ERROR.");
        }
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        Object resp = pjp.proceed();
        Instant end = Instant.now();
        logger.info("RESPONSE : " + JSON.toJSONString(resp));
        logger.info("SPEND TIME : " + Duration.between(start, end));
        return resp;
    }
}
