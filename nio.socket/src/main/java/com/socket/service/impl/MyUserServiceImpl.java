package com.socket.service.impl;

import com.study.base.nio.ser.domain.User;

import com.socket.service.MyUserService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyUserServiceImpl implements MyUserService {

    public List<User> list(int size) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < size; i++) {
            users.add(new User("user_" + i, "password_" + i));
        }
        return users;
    }

    public User findByName(String name) {
        return new User(name, null);
    }

    public void test() {
        // do nothing
    }
}
