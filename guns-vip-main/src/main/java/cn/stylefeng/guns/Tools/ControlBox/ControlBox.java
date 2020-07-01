package cn.stylefeng.guns.Tools.ControlBox;

import cn.stylefeng.guns.config.TcpConfig;
import cn.stylefeng.guns.server.bll.listener.ServerThread;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.*;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/16 16:08
 * @description：
 * @modified By：
 * @version: $
 */
public class ControlBox extends Thread {
    private static DatagramSocket datagramSocket;
    private static final int TIMEOUT = 5000;  //设置接收数据的超时时间
    private static final int MAXNUM = 5;      //设置重发数据的最多次数
    private static byte[] command_open = new byte[]{00, 00, 00, 00, 00, 15, 01, (byte) 16, 00, (byte) 80, 00, 04, (byte) 8, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255};
    private static byte[] command_close = new byte[]{00, 00, 00, 00, 00, 15, 01, (byte) 16, 00, (byte) 80, 00, 04, (byte) 8, 0, 0, 0, 0, 0, 0, 0, 0};

    public ControlBox() throws Exception {
        Init();
    }

    private void Init() throws SocketException {
        ApplicationContext context = new AnnotationConfigApplicationContext(TcpConfig.class);
        String pt = context.getApplicationName();
        int port = 60000;
        try {
            port = Integer.parseInt(context.getBean("socketPort_Udp").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //服务端在3000端口监听接收到的数据
        datagramSocket = new DatagramSocket(60000);
        Object a = "121".getBytes();

        this.start();
        System.out.println("服务器启动!");
    }

    @Synchronized
    public static void Send(String ip, int key) throws IOException {
        String[] ipStr = ip.split("\\.");
        byte[] ipBuf = new byte[4];
        for (int i = 0; i < 4; i++) {
            ipBuf[i] = (byte) (Integer.parseInt(ipStr[i]) & 0xff);
        }

        InetAddress ipAddress = InetAddress.getByAddress(ipBuf);
        switch (key) {
            case 1:
                SendCommand(ipAddress, command_open);
                break;
            case 0:
                SendCommand(ipAddress, command_close);
                break;
        }
    }

    @Synchronized
    private static void SendCommand(InetAddress ipAddress, byte[] command) {
        try {
            DatagramPacket dp_send = new DatagramPacket(command, command.length, ipAddress, 502);
            datagramSocket.send(dp_send);
        } catch (Exception ex) {

        }
    }

    @SneakyThrows
    @Override
    public void run() {
        byte[] buf = new byte[1024];
        //接收从客户端发送过来的数据
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        System.out.println("server is on，waiting for client to send data......");
        boolean f = true;
        while (f) {
            //服务器端接收来自客户端的数据
            datagramSocket.receive(dp_receive);
            System.out.println("server received data from client：");
//            String str_receive = new String(dp_receive.getData(), 0, dp_receive.getLength()) +
//                    " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
//            System.out.println(str_receive);
//            //数据发动到客户端的3000端口
//            DatagramPacket dp_send = new DatagramPacket(str_send.getBytes(), str_send.length(), dp_receive.getAddress(), 9000);
//            datagramSocket.send(dp_send);
//            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
//            //所以这里要将dp_receive的内部消息长度重新置为1024
//            dp_receive.setLength(1024);
        }
        datagramSocket.close();
    }
}
