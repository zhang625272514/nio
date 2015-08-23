package com.study.base.nio.server;

import com.study.base.nio.ser.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by cheng on 2015/8/23.
 * 传输对象
 * （1） 首先需要一个普通的对象类，由于需要序列化这个对象以便在网络上传输，所以实现java.io.Serializable接口就是必不可少的了；
 * （2） 对于Server端的代码，代码中分别使用了ObjectInputStream和ObjectOutputStream来接收和发送socket中的InputStream和OutputStream，然后转换成Java对象；
 * （3） Client也和Server端类似，同样使用ObjectOutputStream和ObjectInputStream来处理
 */
public class MyServerTransObj {
    private final static Logger logger = LoggerFactory.getLogger(MyServerTransObj.class.getName());

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(10000);

        while (true) {
            Socket socket = server.accept();
            invoke(socket);
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
                    logger.error(null, ex);
                } catch(ClassNotFoundException ex) {
                    logger.error(null, ex);
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
