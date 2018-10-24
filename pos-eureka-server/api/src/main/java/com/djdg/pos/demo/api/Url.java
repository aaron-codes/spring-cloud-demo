/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */

package com.djdg.pos.demo.api;

/**
 * Created by Mr.OuYangX, 2017/12/14 下午6:15.
 */
public interface Url {

    String SERVICE_NAME = "POS-DEMO";

    String SERVICE_HOSTNAME = "http://" + SERVICE_NAME;

    String MODULE_PATH = "/ja/demo/";

    interface V1 {

        String REG_USER = MODULE_PATH + "v1/users";
        String GET_USER = MODULE_PATH + "v1/users/{userId}";

    }

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }
}
