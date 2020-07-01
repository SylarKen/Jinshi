package cn.stylefeng.guns.modular.form.controller;

import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.WeighbridgeConfig;
import cn.stylefeng.guns.modular.form.service.WeighbridgeConfigService;
import cn.stylefeng.guns.modular.form.wrapper.WeighbridgeConfigWrapper;
import cn.stylefeng.guns.server.bll.model.datafactory.Response;
import cn.stylefeng.guns.sys.core.constant.dictmap.UserDict;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 地磅管理controller层
 */
@Controller
@RequestMapping("/weighbridgeConfig")
public class WeighbridgeConfigController extends BaseController {

    @Autowired
    private WeighbridgeConfigService service;

    private String PREFIX = "/weighbridgeConfig";

    /**
     * 跳转地磅页面
     */
    @GetMapping("/index")
    public String toIndex() {
        return PREFIX + "/index.html";
    }

    /**
     * 查询列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Object queryList() {
        Page<Map<String, Object>> page = this.service.queryWightConfig();
        Page wrap = new WeighbridgeConfigWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String add() {
        return PREFIX + "/add.html";
    }

    /**
     *编辑页面
     */
    @GetMapping("/edit")
    public String edit() {
        return PREFIX + "/edit.html";
    }

    /**
     * 新增地磅
     */
    @PostMapping("/addWeighbridgeConfig")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseData add(WeighbridgeConfig wc) {
        boolean b = this.service.addWeighbridgeConfig(wc);
        if (b){
            return SUCCESS_TIP;
        }
        return ErrorResponseData.error("增加失败");
    }

    /**
     * 删除地磅
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam("wbcId") Long wbcId) {
        if (ToolUtil.isEmpty(wbcId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Boolean b = this.service.delete(wbcId);
        if (b) {
            return SUCCESS_TIP;
        }
        return new ErrorResponseData("删除失败");
    }
    /**
     * 查询地磅信息
     */
    @PostMapping("/getInfo")
    @ResponseBody
    public WeighbridgeConfig getInfo(@RequestParam Long id) {
        WeighbridgeConfig wc = this.service.getInfo(id);
        return wc;
    }
    /**
     * 更新地磅
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseData updateByDetail(WeighbridgeConfig weighbridgeConfig) {
        boolean b = this.service.updateByDetail(weighbridgeConfig);
        if (b){
            return SUCCESS_TIP;
        }
        return ErrorResponseData.error("更新失败");
    }

    @GetMapping("/getAllIpCamera")
    @ResponseBody
    public ResponseData getAllIpCamera() {
        List<WeighbridgeConfig> list = this.service.getAllIpCamera();
        return ResponseData.success(list);
    }
}
