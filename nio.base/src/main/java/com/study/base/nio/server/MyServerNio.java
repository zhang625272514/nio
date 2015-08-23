package com.study.base.nio.server;

import com.study.base.nio.ser.domain.MyRequestObject;
import com.study.base.nio.ser.domain.MyResponseObject;
import com.study.utils.ser.SerializableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by cheng on 2015/8/23.
 *  使用NIO包实现Socket通信
 *  java.nio包是Java在1.4之后增加的，用来提高I/O操作的效率。在nio包中主要包括以下几个类或接口:
 *  Buffer：缓冲区，用来临时存放输入或输出数据。
 *  Charset：用来把Unicode字符编码和其它字符编码互转。
 *   Channel：数据传输通道，用来把Buffer中的数据写入到数据源，或者把数据源中的数据读入到Buffer。
 *  Selector：用来支持异步I/O操作，也叫非阻塞I/O操作。

    nio包中主要通过下面两个方面来提高I/O操作效率:
 *  通过Buffer和Channel来提高I/O操作的速度。
 *  通过Selector来支持非阻塞I/O操作。

 */
public class MyServerNio {

    private final static Logger logger = LoggerFactory.getLogger(MyServerNio.class.getName());

    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;

        try {
            // Selector for incoming time requests
            selector = Selector.open();

            // Create a new server socket and set to non blocking mode
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            // Bind the server socket to the local host and port
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.socket().bind(new InetSocketAddress(10000));

            // Register accepts on the server socket with the selector. This
            // step tells the selector that the socket wants to be put on the
            // ready list when accept operations occur, so allowing multiplexed
            // non-blocking I/O to take place.
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // Here's where everything happens. The select method will
            // return when any operations registered above have occurred, the
            // thread has been interrupted, etc.
            while (selector.select() > 0) {
                // Someone is ready for I/O, get the ready keys
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                // Walk through the ready keys collection and process date requests.
                while (it.hasNext()) {
                    SelectionKey readyKey = it.next();
                    it.remove();

                    // The key indexes into the selector so you
                    // can retrieve the socket that's ready for I/O
                    execute((ServerSocketChannel) readyKey.channel());
                }
            }
        } catch (ClosedChannelException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } finally {
            try {
                selector.close();
            } catch(Exception ex) {}
            try {
                serverSocketChannel.close();
            } catch(Exception ex) {}
        }
    }

    private static void execute(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            MyRequestObject myRequestObject = receiveData(socketChannel);
            logger.info(myRequestObject.toString());

            MyResponseObject myResponseObject = new MyResponseObject(
                    "response for " + myRequestObject.getName(),
                    "response for " + myRequestObject.getValue());
            sendData(socketChannel, myResponseObject);
            logger.info(myResponseObject.toString());
        } finally {
            try {
                socketChannel.close();
            } catch(Exception ex) {}
        }
    }

    private static MyRequestObject receiveData(SocketChannel socketChannel) throws IOException {
        MyRequestObject myRequestObject = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            byte[] bytes;
            int size = 0;
            while ((size = socketChannel.read(buffer)) >= 0) {
                buffer.flip();
                bytes = new byte[size];
                buffer.get(bytes);
                baos.write(bytes);
                buffer.clear();
            }
            bytes = baos.toByteArray();
            Object obj = SerializableUtil.toObject(bytes);
            myRequestObject = (MyRequestObject)obj;
        } finally {
            try {
                baos.close();
            } catch(Exception ex) {}
        }
        return myRequestObject;
    }

    private static void sendData(SocketChannel socketChannel, MyResponseObject myResponseObject) throws IOException {
        byte[] bytes = SerializableUtil.toBytes(myResponseObject);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        socketChannel.write(buffer);
    }
}
