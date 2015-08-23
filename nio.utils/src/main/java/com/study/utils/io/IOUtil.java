package com.study.utils.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by cheng on 2015/8/23.
 */
public class IOUtil {
    public static void closeQuietly(InputStream is) {
        try {
            is.close();
        } catch (Exception e) {
        }
    }

    public static void closeQuietly(OutputStream os) {
        try {
            os.close();
        } catch (Exception e) {
        }
    }

    public static void closeQuietly(Socket socket) {
        try {
            socket.close();
        } catch (Exception e) {
        }
    }
}
