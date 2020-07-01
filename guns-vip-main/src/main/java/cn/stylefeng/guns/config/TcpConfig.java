package cn.stylefeng.guns.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ：Sylar
 * @date ：Created in 2019/12/21 11:01
 * @description：
 * @modified By：
 * @version: $
 */
@Configuration
@Data
@PropertySource("classpath:tcpsocket.properties")
public class TcpConfig {
    @Value("${tcpsocket.port}")
    private String SocketPort;

    @Value("${tcpsocket.port_udp}")
    private String SocketPort_Udp;

    @Value("${add.seconds}")
    private String seconds;

    @Bean(name = "socketPort")
    public String GetSocketPort(){
        return  SocketPort;
    }

    @Bean(name = "socketPort_Udp")
    public String GetSocketPort_Udp(){
        return  SocketPort_Udp;
    }

    @Bean(name = "seconds")
    public String GetSeconds() {
        return seconds;
    }
}
