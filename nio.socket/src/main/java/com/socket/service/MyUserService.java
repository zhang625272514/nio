package com.socket.service;

import com.study.base.nio.ser.domain.User;

import java.util.List;

/**
 * Created by cheng on 2015/8/23.
 */
public interface MyUserService {

    List<User> list(int size);

    User findByName(String name);

    void test();
}
