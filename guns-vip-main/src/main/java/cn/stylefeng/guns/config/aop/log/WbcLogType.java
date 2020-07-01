package cn.stylefeng.guns.config.aop.log;

import lombok.Data;

/**
 * @Author: rednoob
 * @Describe:
 */
public enum WbcLogType {
   Insert(1), Update(2), Delete(3);
   private Integer code;

    WbcLogType(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
