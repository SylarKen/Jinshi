package cn.stylefeng.guns.modular.form.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.form.entity.WbcLog;
import cn.stylefeng.guns.modular.form.mapper.WbcLogMapper;
import cn.stylefeng.guns.modular.form.service.DataLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 日志业务层
 */
@Service
public class DataLogServiceImpl extends ServiceImpl<WbcLogMapper, WbcLog> implements DataLogService {
    @Override
    public Page<Map<String, Object>> queryList(String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.queryDataLog(page, beginTime, endTime);
    }
}
