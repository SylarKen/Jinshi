package cn.stylefeng.guns.modular.form.service;

import onbon.bx05.Bx5GException;

import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface FlowsService {

    /**
     * 执行业务
     */
    void handle(Map<String, String> map) throws Bx5GException;
}
