package cn.stylefeng.guns.config.aop;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.dict.AbstractDictMap;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.config.aop.log.WbcLogType;
import cn.stylefeng.guns.modular.form.entity.WbcLog;
import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.core.util.Contrast;
import cn.stylefeng.roses.core.util.HttpContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 地磅数据管理日志记录AOP
 */
@Aspect
@Component
public class WbcLogRecordAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(cn.stylefeng.guns.config.aop.WbcLogRecord)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordWbcLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }
        return result;
    }

    private void handle(ProceedingJoinPoint point) throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果当前用户未登录，不做日志
        LoginUser user = LoginContextHolder.getContext().getUser();
        if (null == user) {
            return;
        }
        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        WbcLogRecord annotation = currentMethod.getAnnotation(WbcLogRecord.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        Class dictClass = annotation.dict();
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }
        System.out.println(sb);
        //如果涉及到修改,比对变化
        String msg;
        Integer type = 0;
        String carName = "";
        String username = "";
        String content = "";
        if (bussinessName.contains("修改") || bussinessName.contains("编辑")) {
            Object obj1 = LogObjectHolder.me().get();
            Map<String, String> obj2 = HttpContext.getRequestParameters();
            msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
            type = WbcLogType.Update.getCode();
            WeighRecord obj11 = (WeighRecord) obj1;
            carName = obj11.getPlateNumber();
            username = user.getName();
            content = "修改人ID:"+user.getId()+";修改前:"+obj1+";修改后"+obj2;
         } else {
            Map<String, String> obj2 = HttpContext.getRequestParameters();
           carName = obj2.get("plateNumber");
           username = user.getName();
           type = WbcLogType.Delete.getCode();
           content = bussinessName + "删除人ID:"+user.getId()+";删除:"+obj2;
        }
        //启动任务,异步插入
        LogManager.me().executeLog(WbcLogTaskFactory.WbcLog(carName, type, username, content));
    }

}
