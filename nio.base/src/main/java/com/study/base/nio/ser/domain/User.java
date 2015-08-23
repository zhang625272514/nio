package com.study.base.nio.ser.domain;

import java.io.Serializable;

/**
 * Created by cheng on 2015/8/23.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3708228342799939157L;

    private String name;
    private String password;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
