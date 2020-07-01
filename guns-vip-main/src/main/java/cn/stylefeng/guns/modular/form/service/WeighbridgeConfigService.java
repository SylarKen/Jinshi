package cn.stylefeng.guns.modular.form.service;


import cn.stylefeng.guns.Model.Weighbridge_Model;
import cn.stylefeng.guns.modular.form.entity.WeighbridgeConfig;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface WeighbridgeConfigService {
    /**
     * 查询
     */
    Page<Map<String, Object>> queryWightConfig();

    List<WeighbridgeConfig> queryAllWightConfig();

    /**
     * 添加
     * @param weighbridgeConfig
     */
    boolean addWeighbridgeConfig(WeighbridgeConfig weighbridgeConfig);

    /**
     * 更新
     * @param weighbridgeConfig
     */
    boolean updateByDetail(WeighbridgeConfig weighbridgeConfig);

    /**
     * 删除
     * @param wbcId
     */
    Boolean delete(Long wbcId);

    /**
     * 查询单个地磅信息
     * @param id
     */
    WeighbridgeConfig getInfo(Long id);

    List<WeighbridgeConfig> getAllIpCamera();
}
