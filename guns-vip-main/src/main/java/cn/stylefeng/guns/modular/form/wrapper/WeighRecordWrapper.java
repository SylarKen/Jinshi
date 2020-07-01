package cn.stylefeng.guns.modular.form.wrapper;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public class WeighRecordWrapper extends BaseControllerWrapper {
    public WeighRecordWrapper(Map<String, Object> single) {
        super(single);
    }

    public WeighRecordWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public WeighRecordWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public WeighRecordWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {

    }
}
