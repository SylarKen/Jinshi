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
public class WorkAreaWrapper extends BaseControllerWrapper {
    public WorkAreaWrapper(Map<String, Object> single) {
        super(single);
    }

    public WorkAreaWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public WorkAreaWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public WorkAreaWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {

    }
}
