package cn.stylefeng.guns.modular.form.mapper;

import cn.stylefeng.guns.modular.form.entity.WorkArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface WorkAreaMapper extends BaseMapper<WorkArea> {
    /**
     * 查询所有
     * @return
     */
    List<WorkArea> queryAllWorkArea();

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<Map<String, Object>> queryWorkArea(@Param("page") Page page);

    int addWorkArea(WorkArea workArea);

    int deleteWorkAreaById(@Param("id") Integer id);

    WorkArea selectWorkAreaById(Integer id);

    int updateWorkArea(WorkArea workArea);
}
