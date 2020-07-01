package cn.stylefeng.guns.server.component;

import cn.stylefeng.guns.server.bll.listener.TCPServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/21 11:15
 * @description：
 * @modified By：
 * @version: $
 */
//@Component
public class ServerSocket extends Thread implements InitializingBean {
    @Override
    public void run() {
        try {
            Thread serverThread = new Thread(() -> {
                TCPServer sv = null;
                try {
                    sv = new TCPServer();
//                    sv.service();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Thread Running");

            });

            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
}
