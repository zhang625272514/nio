package com.socket.response;

/**
 * Created by cheng on 2015/8/23.
 */
public interface MyResponseHandler<T> {

    T handle(MyResponse response);
}
