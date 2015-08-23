package com.study.base.nio.ser.domain;

import java.io.Serializable;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyRequestObject implements Serializable {
    private static final long serialVersionUID = -4448373044606333201L;

    private String name;

    private String value;

    private byte[] bytes;

    public MyRequestObject(String name, String value) {
        this.name = name;
        this.value = value;
        this.bytes = new byte[1024];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Request [name: " + name  + ", value: " + value + ", bytes: " + bytes.length+ "]");
        return sb.toString();
    }
}
