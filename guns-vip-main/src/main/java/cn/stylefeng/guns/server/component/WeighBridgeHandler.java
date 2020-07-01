package cn.stylefeng.guns.server.component;

import cn.stylefeng.guns.Tools.Led.LedHandler;
import cn.stylefeng.guns.modular.form.entity.WeighbridgeConfig;
import cn.stylefeng.guns.modular.form.service.impl.WeighbridgeConfigServiceImpl;
import cn.stylefeng.guns.server.bll.listener.TCPServer;
import cn.stylefeng.guns.server.bll.model.WeighBridge;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/9 15:28
 * @description：全局变量，地磅
 * @modified By：
 * @version: $
 */
@Component
public class WeighBridgeHandler extends Thread implements InitializingBean {
    @Autowired
    WeighbridgeConfigServiceImpl service_WeighbridgeConfig;

    public static WeighBridgeHandler _this;
    public static Map<String, WeighBridge> bridges = new HashMap<String, WeighBridge>();
    public static Map<String, WeighBridge> bridges_camera = new HashMap<String, WeighBridge>();
    public static Map<String, BigDecimal> weight = new HashMap<>();
    private TCPServer tcpServer;

    @PostConstruct
    public void init() {
        _this = this;
        _this.service_WeighbridgeConfig = this.service_WeighbridgeConfig;
//        tcpPackage.service_sender = this.service_sender;
//        tcpPackage.service_sender_conig = this.service_sender_conig;
    }

    public static void RefreshConfig() {

    }

    private void GetConfigs() {
        List<WeighbridgeConfig> config = _this.service_WeighbridgeConfig.queryAllWightConfig();
        for (WeighbridgeConfig c : config) {
            WeighBridge bridge = new WeighBridge(c);
            bridges.put(c.getIpMeter(), bridge);
            bridges_camera.put(c.getIpCamera(), bridge);
            try {
                LedHandler.Add(c.getIpLed());
            }catch (Exception ex){

            }

        }
    }

    public static WeighBridge GetBridge_ByCamera(String camera_ip) {
        WeighBridge bridge = WeighBridgeHandler.bridges_camera.get(camera_ip);
//        BigDecimal weight = bridge.getWeight();
        return bridge;
    }

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
//        try {
//            if (tcpServer == null) {
//                tcpServer = new TCPServer();
//            }
////            if (tcpServer == null) {
////                tcpServer = new TCPServer();
////                System.out.println("TCPServer Init");
////            } else {
////
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GetConfigs();
        start();
    }
}
