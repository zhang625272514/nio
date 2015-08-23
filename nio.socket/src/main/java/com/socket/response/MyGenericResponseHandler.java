package com.socket.response;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyGenericResponseHandler<T> implements MyResponseHandler<T>  {

    public T handle(MyResponse response) {
        return (T) response.getResult();
    }
}
