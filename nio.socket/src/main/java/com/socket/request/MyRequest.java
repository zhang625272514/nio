package com.socket.request;

import java.io.Serializable;

/**
 * Created by cheng on 2015/8/23.
 */
public interface MyRequest extends Serializable{

    Class<?> getRequestClass();

    String getRequestMethod();

    Class<?>[] getRequestParameterTypes();

    Object[] getRequestParameterValues();

}
