package com.study.base.nio.server;

import com.study.base.nio.ser.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by cheng on 2015/8/23.
 * SSL 通讯
 * SSL Server类，这里需要用到ServerSocketFactory类来创建SSLServerSocket类实例，
 * 然后在通过SSLServerSocket来获取SSLSocket实例，这里考虑到面向对象中的面向接口编程的理念，
 * 所以代码中并没有出现SSLServerSocket和SSLSocket，而是用了他们的父类ServerSocket和Socket。
 * 在获取到ServerSocket和Socket实例以后，剩下的代码就和不使用加密方式一样了
 */
public class MyServerSsl {

    private static final Logger log = LoggerFactory.getLogger(MyServerSsl.class);

    public static void main(String[] args) {
        try {
            ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
            ServerSocket server = factory.createServerSocket(10000);

            while (true) {
                Socket socket = server.accept();
                invoke(socket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void invoke(final Socket socket) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                ObjectInputStream is = null;
                ObjectOutputStream os = null;
                try {
                    is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                    os = new ObjectOutputStream(socket.getOutputStream());

                    Object obj = is.readObject();
                    User user = (User)obj;
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());

                    user.setName(user.getName() + "_new");
                    user.setPassword(user.getPassword() + "_new");

                    os.writeObject(user);
                    os.flush();
                } catch (IOException ex) {
                    log.info(null, ex);
                } catch(ClassNotFoundException ex) {
                    log.info(null, ex);
                } finally {
                    try {
                        is.close();
                    } catch(Exception ex) {}
                    try {
                        os.close();
                    } catch(Exception ex) {}
                    try {
                        socket.close();
                    } catch(Exception ex) {}
                }
            }
        }).start();
    }
}
