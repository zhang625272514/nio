package com.study.base.nio.client;

import com.study.base.nio.ser.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by cheng on 2015/8/23.
 *
 * Client也和Server端类似，同样要不socket的XXXStream包装成GZIPXXXStream，然后再包装成ObjectXXXStream
 */
public class MyClientGzip {

    private final static Logger logger = LoggerFactory.getLogger(MyClientGzip.class.getName());

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Socket socket = null;
            GZIPOutputStream gzipos = null;
            ObjectOutputStream oos = null;
            GZIPInputStream gzipis = null;
            ObjectInputStream ois = null;

            try {
                socket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("localhost", 10000);
                socket.connect(socketAddress, 10 * 1000);
                socket.setSoTimeout(10 * 1000);

                gzipos = new GZIPOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(gzipos);
                User user = new User("user_" + i, "password_" + i);
                oos.writeObject(user);
                oos.flush();
                gzipos.finish();

                gzipis = new GZIPInputStream(socket.getInputStream());
                ois = new ObjectInputStream(gzipis);
                Object obj = ois.readObject();
                if (obj != null) {
                    user = (User)obj;
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());
                }
            } catch(IOException ex) {
                logger.error(null, ex);
            } finally {
                try {
                    ois.close();
                } catch(Exception ex) {}
                try {
                    oos.close();
                } catch(Exception ex) {}
                try {
                    socket.close();
                } catch(Exception ex) {}
            }
        }
    }
}
