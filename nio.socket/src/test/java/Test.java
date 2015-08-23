package java;

import com.socket.client.MyClient;
import com.socket.client.impl.MyClientNIOImpl;
import com.socket.client.impl.MyClientSimpleImpl;
import com.socket.request.MyGenericRequest;
import com.socket.request.MyRequest;
import com.socket.response.MyGenericResponseHandler;
import com.socket.response.MyResponse;
import com.socket.server.MyServer;
import com.socket.server.impl.MyServerNIOImpl;
import com.socket.server.impl.MyServerSimpleImpl;
import com.socket.service.impl.MyUserServiceImpl;
import com.study.base.nio.ser.domain.User;

import java.util.List;

/**
 * Created by cheng on 2015/8/23.
 */
public class Test {

    private static int port = 10000;

    public static void main(String[] args) throws Exception {
        //test1();
        test2();
    }

    public static void test1() throws Exception {
        MyServer myServer = new MyServerSimpleImpl(port);
        myServer.startup();
        Thread.sleep(3000);

        MyClient myClient = new MyClientSimpleImpl("localhost", port);

        MyRequest request = null;
        MyResponse response = null;

        request = new MyGenericRequest(MyUserServiceImpl.class, "list", new Class<?>[]{int.class}, new Object[]{2});
        response = myClient.execute(request);
        System.out.println(response.getResult());
        List<User> users = myClient.execute(request, new MyGenericResponseHandler<List<User>>());
        System.out.println(users);

        request = new MyGenericRequest(MyUserServiceImpl.class, "findByName", new Class<?>[]{String.class}, new Object[]{"kongxx"});
        response = myClient.execute(request);
        System.out.println(response.getResult());
        User user = myClient.execute(request, new MyGenericResponseHandler<User>());
        System.out.println(user);

        response = myClient.execute(new MyGenericRequest(MyUserServiceImpl.class, "test", new Class<?>[]{}, new Object[]{}));
        System.out.println(response.getResult());
    }

    public static void test2() throws Exception {
        MyServer myServer = new MyServerNIOImpl(port);
        myServer.startup();
        Thread.sleep(3000);

        MyClient myClient = new MyClientNIOImpl("localhost", port);

        MyRequest request = null;
        MyResponse response = null;

        request = new MyGenericRequest(MyUserServiceImpl.class, "list", new Class<?>[]{int.class}, new Object[]{2});
        response = myClient.execute(request);
        System.out.println(response.getResult());
        List<User> users = myClient.execute(request, new MyGenericResponseHandler<List<User>>());
        System.out.println(users);

        request = new MyGenericRequest(MyUserServiceImpl.class, "findByName", new Class<?>[]{String.class}, new Object[]{"kongxx"});
        response = myClient.execute(request);
        System.out.println(response.getResult());
        User user = myClient.execute(request, new MyGenericResponseHandler<User>());
        System.out.println(user);

        response = myClient.execute(new MyGenericRequest(MyUserServiceImpl.class, "test", new Class<?>[]{}, new Object[]{}));
        System.out.println(response.getResult());
    }
}
