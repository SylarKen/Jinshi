package cn.stylefeng.guns.modular.form.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.WorkArea;
import cn.stylefeng.guns.modular.form.service.WorkAreaService;
import cn.stylefeng.guns.modular.form.wrapper.WeighbridgeConfigWrapper;
import cn.stylefeng.guns.modular.form.wrapper.WorkAreaWrapper;
import cn.stylefeng.roses.kernel.model.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 工区controller
 */
@Controller
@RequestMapping("/workArea")
public class WorkAreaController {

    @Autowired
    private WorkAreaService service;

    private String PREFIX = "/workArea";
    /**
     * 获取所有的工区填充下拉框
     * @return
     */
    @GetMapping("/queryAllWorkArea")
    public @ResponseBody ResponseData queryAllWorkArea() {
        List<WorkArea> workAreas = this.service.queryAllWorkArea();
        if (CollectionUtils.isNotEmpty(workAreas)) {
            return ResponseData.success(workAreas);
        }
        return ErrorResponseData.error("查询失败");
    }
    @GetMapping("/index")
    public String index() {
        return PREFIX + "/index.html";
    }

    @GetMapping("/add")
    public String add() {
        return PREFIX + "/add.html";
    }

    @GetMapping("/edit")
    public String edit() {
        return PREFIX + "/edit.html";
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/queryWorkArea")
    public @ResponseBody Object queryWorkArea() {
        Page<Map<String, Object>> page = this.service.queryWorkArea();
        Page wrap = new WorkAreaWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 添加工区
     * @param workArea
     * @return
     */
    @PostMapping("/addWorkArea")
    public @ResponseBody ResponseData addWorkArea(WorkArea workArea) {
        Boolean b = this.service.addWorkArea(workArea);
        if (b) {
            return SuccessResponseData.success();
        }
        return ErrorResponseData.error("添加失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public @ResponseBody ResponseData deleteWorkArea(@RequestParam("workAreaId") Integer id) {
        Boolean b = this.service.deleteWorkArea(id);
        if (b) {
            return SuccessResponseData.success();
        }
        return ErrorResponseData.error("删除失败, 可能有关联车辆");
    }

    /**
     * 获取单个工区信息
     * @return
     */
    @PostMapping("/getInfo")
    public @ResponseBody WorkArea getInfo(Integer id) {
        return this.service.getInfo(id);
    }
    /**
     * 更新
     */
    @PostMapping("/update")
    public @ResponseBody ResponseData updateWorkArea(WorkArea workArea) {
        Boolean b = this.service.updateWorkArea(workArea);
        if (b) {
            return ResponseData.success();
        }
        return ErrorResponseData.error("更新失败");
    }

}
