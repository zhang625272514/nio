package com.socket.client.impl;

import com.socket.client.MyClient;
import com.socket.request.MyRequest;
import com.socket.response.MyResponse;
import com.socket.response.MyResponseHandler;
import com.study.utils.io.IOUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyClientSimpleImpl implements MyClient {

    private String host;
    private int port;

    public MyClientSimpleImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <T> T execute(MyRequest request, MyResponseHandler<T> handler) {
        MyResponse response = execute(request);
        return handler.handle(response);
    }

    public MyResponse execute(MyRequest request) {
        MyResponse response = null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress, 10 * 1000);

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            if (obj != null) {
                response = (MyResponse)obj;
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            IOUtil.closeQuietly(ois);
            IOUtil.closeQuietly(oos);
            IOUtil.closeQuietly(socket);
        }
        return response;
    }
}
