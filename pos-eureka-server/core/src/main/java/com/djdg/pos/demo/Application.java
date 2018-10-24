/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.djdg.pos.demo;

import com.djdg.pos.common.spring.WebApplication;
import com.djdg.pos.demo.constant.SystemConsts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(WebApplication.class)
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info(SystemConsts.FAITH);
    }
}
