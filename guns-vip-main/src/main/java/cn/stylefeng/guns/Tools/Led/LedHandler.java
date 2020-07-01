package cn.stylefeng.guns.Tools.Led;

import cn.stylefeng.guns.Tools.Led.Model.*;
import cn.stylefeng.guns.server.bll.model.WeighBridge;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import lombok.Synchronized;
import onbon.bx05.Bx5GEnv;
import onbon.bx05.Bx5GException;
import onbon.bx05.Bx5GScreenClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/20 9:19
 * @description：
 * @modified By：
 * @version: $
 */
@Component
public class LedHandler implements InitializingBean {

    public static Map<String, Led> members = new HashMap<>();
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void afterPropertiesSet() throws Exception {
        Init();
    }

    private static void Init() throws Exception {
        Bx5GEnv.initial("log.properties", 30000);
    }

    public static void Add(String ip) throws Exception {
        Led led = new Led(ip);
        members.put(ip, led);
    }

    @Synchronized
    public static void Test() {

    }

    @Synchronized
    public static void SendMessage(String ip, String message) throws Bx5GException {
        Led led =members.get(ip);
        led.SendText(message);
    }


}

