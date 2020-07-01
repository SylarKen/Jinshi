package cn.stylefeng.guns.modular.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: rednoob
 * @Describe: 注入websocket实例
 */
@Configuration
@Slf4j
public class WebSocketConfig {
    @Profile({"dev", "test"})
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("webSocketServer注入成功");
        return new ServerEndpointExporter();
    }
    //@Bean
    /*public WebSocketClient webSocketClient() {
        MyWebSocketClient w = null;
        try {
            w = new MyWebSocketClient(new URI("ws://localhost:21002/echo"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        w.connect();
        return w;
    }*/

    /*@Bean
    public WebSocketClient webSocketClients() {
        return new WebSocketClient("ws://127.0.0.1:21002/echo");
    }*/
}
