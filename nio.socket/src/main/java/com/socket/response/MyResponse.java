package com.socket.response;

import java.io.Serializable;

/**
 * Created by cheng on 2015/8/23.
 */
public interface MyResponse extends Serializable {

    Object getResult();
}
