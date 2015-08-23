package com.study.base.nio.client;

import com.study.base.nio.ser.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by cheng on 2015/8/23.
 */
public class MyClientTransObj {

    private final static Logger logger = LoggerFactory.getLogger(MyClientTransObj.class.getName());

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            Socket socket = null;
            ObjectOutputStream os = null;
            ObjectInputStream is = null;

            try {
                socket = new Socket("localhost", 10000);

                os = new ObjectOutputStream(socket.getOutputStream());
                User user = new User("user_" + i, "password_" + i);
                os.writeObject(user);
                os.flush();

                is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Object obj = is.readObject();
                if (obj != null) {
                    user = (User)obj;
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());
                }
            } catch(IOException ex) {
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
    }
}
