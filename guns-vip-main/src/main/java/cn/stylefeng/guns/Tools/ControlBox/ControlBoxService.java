package cn.stylefeng.guns.Tools.ControlBox;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/19 9:33
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class ControlBoxService {

    public static void Send_Up(String ip) {
        try {
            Thread thread_send = new Thread(() -> {
                try {
                    ControlBox.Send(ip, 1);
                    Thread.sleep(2000);
                    ControlBox.Send(ip, 0);
                    System.out.println("發送成功");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread_send.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
