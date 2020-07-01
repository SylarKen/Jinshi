package cn.stylefeng.guns.Tools.ControlBox;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/16 16:01
 * @description：继电器控制板
 * @modified By：
 * @version: $
 */
@Component
public class ControlBoxHandler  extends Thread implements InitializingBean {
    private ControlBox controlbox;

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    @Override
    public void run() {
        try {
            if (controlbox == null) {
                controlbox = new ControlBox();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
