package cn.stylefeng.guns.modular.form.service;

import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import cn.stylefeng.guns.modular.form.entity.xlsx.Conditions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface WeighRecordService {

    /**
     * 查询数据管理
     */
    Page<Map<String, Object>> queryWeighRecord(String condition);

    /**
     * 根据Id查询数据
     * @param id
     */
    WeighRecord getInfo(Long id);

    /**
     * 根据Id更新
     * @param weighRecord
     */
    Boolean update(WeighRecord weighRecord);

    /**
     * 根据ID删除
     * @param id
     */
    Boolean delete(Long id);

    Page<Map<String, Object>> queryListByCondition(Conditions condition);
}
