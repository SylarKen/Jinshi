package cn.stylefeng.guns.modular.websocket.echarts;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.guns.modular.form.entity.WeightResult;
import cn.stylefeng.guns.server.bll.model.WeighBridge;
import cn.stylefeng.guns.server.component.WeighBridgeHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: rednoob
 * @Describe: 注入    启动并接收请求
 * 实时推送地磅消息
 */
@ServerEndpoint("/echo/{ipAddr}")
@Component
public class EchartsSocket {
    private static int onlineCount = 0;
    private static Map<String, WeighBridge> map = new HashMap<>();
    private static CopyOnWriteArraySet<EchartsSocket> user = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, EchartsSocket> websocketSet = new ConcurrentHashMap<>();
    private Session session;

    public EchartsSocket() {
    }

    /**
     * @param ipAddr  摄像头ip地址
     * @param session
     */
    @OnOpen
    public void open(@PathParam("ipAddr") String ipAddr, Session session) {
        this.session = session;
        onlineCount++;
        user.add(this);
        WeighBridge bridge = null;
        if (map.containsKey(ipAddr)) {
            bridge = map.get(ipAddr);
        } else {
            // 根据 ip 取地磅读数
            bridge = WeighBridgeHandler.GetBridge_ByCamera(ipAddr);// 获取地磅对象
        }
        WeightResult weightResult = bridge.GetStableWeight();// 获取地磅读数
        //websocketSet.put(ipAddr, this);
        map.put(ipAddr, bridge);
        if (weightResult.getResult()) {
            while (true) {
                try {
                    session.getBasicRemote().sendText(String.valueOf(weightResult.getValues()));
                    //session.getBasicRemote().sendText(String.valueOf(RandomUtil.randomInt(1, 300)));
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {

                }
            }
        } else {
            while (true) {
                try {
                    //session.getBasicRemote().sendText(String.valueOf(bridge.GetStableWeight()));
                    session.getBasicRemote().sendText(String.valueOf(RandomUtil.randomInt(1, 300)));
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {

                }
            }
        }

    }

    @OnClose
    public void close(Session session) {
        user.remove(this);
        try {
            session.close();
        } catch (IOException e) {
        }
        System.out.println("session" + session.getId() + "关闭");
    }

    @OnMessage
    public void message(Session session, String message) throws IOException {
        //session.getBasicRemote().sendText("ackMsg");
    }

    @OnError
    public void onerror(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.error("客户端突然失去连接了");
    }

}
