package cn.stylefeng.guns.config.aop;

import cn.stylefeng.guns.base.dict.AbstractDictMap;
import cn.stylefeng.guns.base.dict.SystemDict;

import java.lang.annotation.*;

/**
 * @Author: rednoob
 * @Describe: 地磅数据管理日志记录所需注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WbcLogRecord {
    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";

    Class<? extends AbstractDictMap> dict() default SystemDict.class;
}
