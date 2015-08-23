package com.socket.response;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyGenericResponse implements  MyResponse {
    private Object obj = null;

    public MyGenericResponse(Object obj) {
        this.obj = obj;
    }

    public Object getResult() {
        return obj;
    }
}
