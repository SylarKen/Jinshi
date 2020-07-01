package cn.stylefeng.guns.config.aop;

import cn.stylefeng.guns.modular.form.entity.WbcLog;
import cn.stylefeng.guns.modular.form.mapper.WbcLogMapper;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * @Author: rednoob
 * @Describe: 任务类
 */
public class WbcLogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    //引入mapper
    private static WbcLogMapper wbcLogMapper = SpringContextHolder.getBean(WbcLogMapper.class);
    //任务作业
    public static TimerTask WbcLog(String carName, Integer type, String userName, String content) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    WbcLog wbcLog = WbcLogFactory.createWbcLog(carName, type, userName, content);
                    wbcLogMapper.insertSelective(wbcLog);
                } catch (Exception e) {
                    logger.error("创建数据管理日志异常!", e);
                }
            }
        };
    }
}
