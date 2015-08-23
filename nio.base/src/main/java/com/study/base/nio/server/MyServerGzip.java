package com.study.base.nio.server;

import com.study.base.nio.ser.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by cheng on 2015/8/23.
 * 传输压缩对象
 * (1) socket的InputStream首先被包装成GZIPInputStream，
 * (2) 然后又被包装成ObjectInputStream，而socket的OutputStream首先被包装成GZIPOutputStream，然后又被包装成ObjectOutputStream
 */
public class MyServerGzip {

    private final static Logger logger = LoggerFactory.getLogger(MyServerGzip.class.getName());

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(10000);

        while (true) {
            Socket socket = server.accept();
            socket.setSoTimeout(10 * 1000);
            invoke(socket);
        }
    }

    private static void invoke(final Socket socket) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                GZIPInputStream gzipis = null;
                ObjectInputStream ois = null;
                GZIPOutputStream gzipos = null;
                ObjectOutputStream oos = null;

                try {
                    gzipis = new GZIPInputStream(socket.getInputStream());
                    ois = new ObjectInputStream(gzipis);
                    gzipos = new GZIPOutputStream(socket.getOutputStream());
                    oos = new ObjectOutputStream(gzipos);

                    Object obj = ois.readObject();
                    User user = (User)obj;
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());

                    user.setName(user.getName() + "_new");
                    user.setPassword(user.getPassword() + "_new");

                    oos.writeObject(user);
                    oos.flush();
                    gzipos.finish();
                } catch (IOException ex) {
                    logger.error(null, ex);
                } catch(ClassNotFoundException ex) {
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
        }).start();
    }
}
