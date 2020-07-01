package cn.stylefeng.guns.modular.form.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.service.DataLogService;
import cn.stylefeng.guns.sys.modular.system.warpper.LogWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 日志controller层
 *              只写了查询
 */
@Controller
@RequestMapping("/dataLog")
public class DataLogController {


    private String PREFIX = "/dataLog";


    @Autowired
    private DataLogService service;

    /**
     * 跳转日志页面
     */
    @GetMapping("/index")
    public String toIndex() {
        return PREFIX + "/index.html";
    }

    /**
     * 查询日志
     */
    @GetMapping("/list")
    @ResponseBody
    public Object queryList(@RequestParam(required = false) String beginTime,
                            @RequestParam(required = false) String endTime) {
        Page<Map<String, Object>> page = this.service.queryList(beginTime, endTime);
        Page wrap = new LogWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrap);

    }

}
