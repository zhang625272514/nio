package com.socket.client;

import com.socket.request.MyRequest;
import com.socket.response.MyResponse;
import com.socket.response.MyResponseHandler;

/**
 * Created by cheng on 2015/8/23.
 */
public interface MyClient {
    public <T> T execute(MyRequest request, MyResponseHandler<T> handler);

    public MyResponse execute(MyRequest request);
}
