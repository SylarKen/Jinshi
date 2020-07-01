package cn.stylefeng.guns.server.bll.listener;

import cn.stylefeng.guns.config.TcpConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: weightservice
 * @description: TCP/Socket Class
 * @author: johnny
 * @create: 2018-07-11 14:24
 **/
public class TCPServer extends Thread{

    private ServerSocket serverSocket;
    public static Map<String, String> CurrentConnections = new HashMap<>();

    public TCPServer() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(TcpConfig.class);
        String pt = context.getApplicationName();
        int port = 0;
        try {
            port = Integer.parseInt(context.getBean("socketPort").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        serverSocket = new ServerSocket(port);
        this.start();
        System.out.println("服务器启动!");
    }

    @Override
    public void run() {
        try {
            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            Socket socket = null;
            //记录客户端的数量
            int count = 0;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            //循环监听等待客户端的连接
            while (true) {
                //调用accept()方法开始监听，等待客户端的连接
                socket = serverSocket.accept();
                //创建一个新的线程
                ServerThread serverThread = new ServerThread(socket);


                count++;//统计客户端的数量
                System.out.println("客户端的数量：" + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP：" + address.getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void service() throws Exception {
//        try {
//            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
//            Socket socket = null;
//            //记录客户端的数量
//            int count = 0;
//            System.out.println("***服务器即将启动，等待客户端的连接***");
//            //循环监听等待客户端的连接
//            while (true) {
//                //调用accept()方法开始监听，等待客户端的连接
//                socket = serverSocket.accept();
//                //创建一个新的线程
//                ServerThread serverThread = new ServerThread(socket);
//                //启动线程
//                serverThread.start();
//
//                count++;//统计客户端的数量
//                System.out.println("客户端的数量：" + count);
//                InetAddress address = socket.getInetAddress();
//                System.out.println("当前客户端的IP：" + address.getHostAddress());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
