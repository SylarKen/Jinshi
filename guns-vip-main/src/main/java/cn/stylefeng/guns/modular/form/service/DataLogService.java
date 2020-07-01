package cn.stylefeng.guns.modular.form.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface DataLogService {
    /**
     * 查询日志
     */
    Page<Map<String, Object>> queryList(String beginTime, String endTime);
}
