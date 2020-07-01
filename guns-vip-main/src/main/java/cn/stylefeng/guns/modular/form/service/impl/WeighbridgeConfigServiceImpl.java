package cn.stylefeng.guns.modular.form.service.impl;

import cn.stylefeng.guns.Model.Weighbridge_Model;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.WeighbridgeConfig;
import cn.stylefeng.guns.modular.form.mapper.WeighbridgeConfigMapper;
import cn.stylefeng.guns.modular.form.service.WeighbridgeConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 地磅管理业务层
 */
@Service
public class WeighbridgeConfigServiceImpl extends ServiceImpl<WeighbridgeConfigMapper, WeighbridgeConfig> implements WeighbridgeConfigService {

    @Autowired
    private WeighbridgeConfigMapper dao;

    @Override
    public Page queryWightConfig() {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectWbc(page);
    }

    @Override
    public List<WeighbridgeConfig> queryAllWightConfig() {
        return this.baseMapper.GetConfigList();
    }


    @Override
    public boolean addWeighbridgeConfig(WeighbridgeConfig weighbridgeConfig) {
        if (weighbridgeConfig == null) return false;
        return this.dao.insertSelective(weighbridgeConfig) == 1;
    }

    @Override
    public boolean updateByDetail(WeighbridgeConfig weighbridgeConfig) {
        if (weighbridgeConfig == null) return false;
        return this.dao.updateByPrimaryKeySelective(weighbridgeConfig) == 1;
    }

    @Override
    public Boolean delete(Long wbcId) {
        return this.dao.deleteByPrimaryKey(wbcId.intValue()) == 1;
    }

    @Override
    public WeighbridgeConfig getInfo(Long id) {
        return this.dao.selectByPrimaryKey(id.intValue());
    }

    @Override
    public List<WeighbridgeConfig> getAllIpCamera() {
        return this.dao.getAllIpCamera();
    }
}
