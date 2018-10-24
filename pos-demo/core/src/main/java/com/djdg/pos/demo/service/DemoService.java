package com.djdg.pos.demo.service;

import com.djdg.pos.common.api.Result;
import com.djdg.pos.demo.api.UserDto;
import com.djdg.pos.demo.dao.UserRepo;
import com.djdg.pos.demo.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;


@Service
public class DemoService {

    private static Logger logger = LoggerFactory.getLogger(UserRepo.class);

    @Resource
    private UserRepo userRepo;

    public Result<?> findUser(Long userId) {
        User user = userRepo.findById(userId);
        if(!Objects.isNull(user)) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getId());
            userDto.setUserName(user.getUserName());
            userDto.setPwd(user.getPwd());

            return Result.SuccResult("查询用户成功", userDto);
        }
        return Result.FailedResult("不存在的用户");
    }

    public Result<UserDto> saveUser(UserDto userDto) {
        User user = new User();
        user.setPwd(userDto.getPwd());
        user.setUserName(userDto.getUserName());

        user = userRepo.save(user);
        userDto.setUserId(user.getId());

        return Result.SuccResult("注册用户成功", userDto);

    }

}

