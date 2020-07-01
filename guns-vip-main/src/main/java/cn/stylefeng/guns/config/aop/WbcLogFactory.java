package cn.stylefeng.guns.config.aop;

import cn.stylefeng.guns.config.aop.log.WbcLogType;
import cn.stylefeng.guns.modular.form.entity.WbcLog;

/**
 * @Author: rednoob
 * @Describe:  日志对象
 */
public class WbcLogFactory {
    /**
     * 数据对象
     */
    public static WbcLog createWbcLog(String carName, Integer type, String userName, String content) {
        WbcLog wbcLog = new WbcLog();
        wbcLog.setTitle(carName);
        wbcLog.setType(type);
        wbcLog.setOperuser(userName);
        wbcLog.setContent(content);
        return wbcLog;
    }
}
