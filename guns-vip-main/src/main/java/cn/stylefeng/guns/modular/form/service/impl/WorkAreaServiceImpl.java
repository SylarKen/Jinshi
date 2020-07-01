package cn.stylefeng.guns.modular.form.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.Vehicle;
import cn.stylefeng.guns.modular.form.entity.VehicleExample;
import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import cn.stylefeng.guns.modular.form.entity.WorkArea;
import cn.stylefeng.guns.modular.form.mapper.VehicleMapper;
import cn.stylefeng.guns.modular.form.mapper.WeighRecordMapper;
import cn.stylefeng.guns.modular.form.mapper.WorkAreaMapper;
import cn.stylefeng.guns.modular.form.service.WorkAreaService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 工区业务层
 */
@Service
public class WorkAreaServiceImpl extends ServiceImpl<WorkAreaMapper, WorkArea> implements WorkAreaService {

    @Autowired
    private WorkAreaMapper workAreaDao;

    @Autowired
    private VehicleMapper carDao;

    @Override
    public List<WorkArea> queryAllWorkArea() {
        return this.workAreaDao.queryAllWorkArea();
    }

    @Override
    public Page<Map<String, Object>> queryWorkArea() {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.queryWorkArea(page);
    }

    @Override
    public Boolean addWorkArea(WorkArea workArea) {
        return this.baseMapper.addWorkArea(workArea) == 1;
    }

    @Override
    public Boolean deleteWorkArea(Integer id) {
        WorkArea workArea = this.workAreaDao.selectWorkAreaById(id);
        List<Vehicle> list = this.carDao.selectByWorkArea(workArea.getWorkArea());
        if (list.size() != 0) {
            return false;
        }
        return this.workAreaDao.deleteWorkAreaById(id) == 1;
    }

    @Override
    public WorkArea getInfo(Integer id) {
        return workAreaDao.selectWorkAreaById(id);
    }

    @Override
    public Boolean updateWorkArea(WorkArea workArea) {
        return workAreaDao.updateWorkArea(workArea) == 1;
    }
}
