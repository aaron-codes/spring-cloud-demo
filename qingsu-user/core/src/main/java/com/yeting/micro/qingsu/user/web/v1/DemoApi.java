package com.djdg.pos.demo.web.v1;

import com.djdg.pos.common.api.Result;
import com.djdg.pos.demo.api.Url;
import com.djdg.pos.demo.api.UserDto;
import com.djdg.pos.demo.service.DemoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(description = "Demo-用户相关API")
@RestController
@Validated
public class DemoApi {
    private static final Logger logger = LoggerFactory.getLogger(DemoApi.class);

    @Resource
    private DemoService demoService;

    @ApiOperation(value = "保存用户信息", notes = "")
    @ApiImplicitParam(name = "userDto", value = "保存用户信息", required = true, dataType = "UserDto")

    @PostMapping(value = Url.V1.REG_USER)
    public Result regUser(@RequestBody @Validated UserDto userDto) {
        return demoService.saveUser(userDto);
    }

    @ApiOperation(value = "查询用户信息", notes = "")
    @ApiParam(name = "userId", value = "用户ID", required = true)

    @RequestMapping(value = Url.V1.GET_USER, method = RequestMethod.GET)
    public Result getUser(@PathVariable Long userId) {
        return demoService.findUser(userId);
    }
}
