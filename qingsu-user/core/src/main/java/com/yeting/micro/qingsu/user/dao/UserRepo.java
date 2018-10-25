/*
 * *
 *
 *     Created by OuYangX.
 *     Copyright (c) 2017, ouyangxian@gmail.com All Rights Reserved.
 *
 * /
 */
package com.yeting.micro.qingsu.user.dao;

import com.yeting.micro.qingsu.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Arlen
 * @date 2017-11-24
 */

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select s.id from User s where id=?1 or name=?1")
    List<Long> findByIdOrName(String search);


    @Query(value = "select s from User s where id=?1")
    Optional<User> findById(Long id);

}
