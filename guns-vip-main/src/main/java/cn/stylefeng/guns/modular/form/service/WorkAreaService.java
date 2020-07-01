package cn.stylefeng.guns.modular.form.service;

import cn.stylefeng.guns.modular.form.entity.WorkArea;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface WorkAreaService {
    List<WorkArea> queryAllWorkArea();

    Page<Map<String, Object>> queryWorkArea();

    Boolean addWorkArea(WorkArea workArea);

    Boolean deleteWorkArea(Integer id);

    WorkArea getInfo(Integer id);

    Boolean updateWorkArea(WorkArea workArea);
}
