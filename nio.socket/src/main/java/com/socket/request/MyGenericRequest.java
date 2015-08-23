package com.socket.request;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyGenericRequest implements MyRequest {

    private static final long serialVersionUID = -1785137014213004213L;

    private Class<?> requestClass;
    private String requestMethod;
    private Class<?>[] requestParameterTypes;
    private Object[] requestParameterValues;

    public MyGenericRequest(Class<?> requestClass, String requestMethod, Class<?>[] requestParameterTypes, Object[] requestParameterValues) {
        this.requestClass = requestClass;
        this.requestMethod = requestMethod;
        this.requestParameterTypes = requestParameterTypes;
        this.requestParameterValues = requestParameterValues;
    }

    public Class<?> getRequestClass() {
        return requestClass;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Class<?>[] getRequestParameterTypes() {
        return requestParameterTypes;
    }

    public Object[] getRequestParameterValues() {
        return requestParameterValues;
    }
}
