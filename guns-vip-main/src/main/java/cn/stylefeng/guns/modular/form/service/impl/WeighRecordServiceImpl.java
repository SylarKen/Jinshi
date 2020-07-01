package cn.stylefeng.guns.modular.form.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.Vehicle;
import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import cn.stylefeng.guns.modular.form.entity.xlsx.Conditions;
import cn.stylefeng.guns.modular.form.mapper.VehicleMapper;
import cn.stylefeng.guns.modular.form.mapper.WeighRecordMapper;
import cn.stylefeng.guns.modular.form.service.WeighRecordService;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 数据管理业务层
 */
@Service
public class WeighRecordServiceImpl extends ServiceImpl<WeighRecordMapper, WeighRecord> implements WeighRecordService {
    @Autowired
    private VehicleMapper carDao;

    @Override
    public Page<Map<String, Object>> queryWeighRecord(String condition) {
        if (StringUtils.isEmpty(condition)) {
            condition = null;
        }
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.queryWeighRecord(page, condition);
    }

    @Override
    public WeighRecord getInfo(Long id) {
        if (id == null) return null;
        return this.baseMapper.selectByPrimaryKey(id.intValue());
    }

    @Override
    public Boolean update(WeighRecord weighRecord) {
        if (weighRecord == null) return false;
        return this.baseMapper.updateByPrimaryKeySelective(weighRecord) == 1;
    }

    @Override
    public Boolean delete(Long id) {
        if (id == null) return false;
        return this.baseMapper.deleteByPrimaryKey(id.intValue()) == 1;
    }

    @Override
    public Page<Map<String, Object>> queryListByCondition(Conditions condition) {
        Page page = LayuiPageFactory.defaultPage();
        if (ObjectUtils.isNotEmpty(condition)) {
            String plateNumber = null;
            if (StringUtils.isNotEmpty(condition.getPlateNumber())) {
                Vehicle vehicle = carDao.selectByPrimaryKey(Integer.parseInt(condition.getPlateNumber()));
                plateNumber = vehicle.getPlateNumber();
            }
            return this.baseMapper.queryListByCondition(page, condition.getBeginTime(), condition.getEndTime(), condition.getDay(), condition.getMonth(), condition.getSeason(), condition.getHalf(), condition.getYear(), condition.getAreaCode(), condition.getPoundNumber(), plateNumber);
        }
        return this.baseMapper.queryWeighRecord(page, null);

    }

}
