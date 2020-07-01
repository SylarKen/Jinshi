package cn.stylefeng.guns.modular.form.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.Vehicle;
import cn.stylefeng.guns.modular.form.service.VehicleService;
import cn.stylefeng.guns.modular.form.wrapper.VehicleWrapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import cn.stylefeng.roses.kernel.model.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 车辆controller层
 */
@Controller
@RequestMapping("/vehicle")
public class VehicleController {

    private String PREFIX = "/vehicle";

    @Autowired
    private VehicleService service;

    /**
     * 跳转车辆页面
     */
    @GetMapping("/index")
    public String toIndex() {
        return PREFIX + "/index.html";
    }

    /**
     * 跳转添加页面
     */
    @GetMapping("/add")
    public String toAdd(){
        return PREFIX + "/add.html";
    }

    /**
     * 跳转编辑页面
     */
    @GetMapping("/edit")
    public String toEdit() {
        return PREFIX + "/edit.html";
    }
    /**
     * 添加车辆信息
     */
    @PostMapping("/addVehicle")
    @ResponseBody
    public ResponseData add(Vehicle vehicle){
        Boolean b = this.service.addVehicle(vehicle);
        System.out.println(b);
        if (b){
            return SuccessResponseData.success();
        }
        return ErrorResponseData.error("添加失败, 可能有重复车牌");

    }

    /**
     * 查询车辆
     */
    @GetMapping("/list")
    @ResponseBody
    public Object queryList(@RequestParam(required = false) String plateNumber, @RequestParam(required = false) String vehicleType, @RequestParam(required = false) String workArea) {
        Page<Map<String, Object>>  page = this.service.queryVehicle(plateNumber, vehicleType, workArea);
        Page wrap = new VehicleWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrap);

    }

    /**
     * 删除车辆
     */
    @PostMapping("/delete")
    public @ResponseBody ResponseData delete(@RequestParam(value = "id") Long carId) {
        Boolean b = this.service.deleteVehicleById(carId);
        if (b){
            return SuccessResponseData.success();
        }
        return ErrorResponseData.error("删除失败");
    }
    /**
     * 查询车辆信息
     */
    @PostMapping("/getInfo")
    @ResponseBody
    public Vehicle getInfo(@RequestParam Long id) {
        Vehicle car = this.service.getInfo(id);
        return car;
    }
    /**
     * 更新车辆信息
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseData update(Vehicle car) {
        Boolean b = this.service.updateByDetail(car);
        if (b) {
            return ResponseData.success();
        }
        return ErrorResponseData.error("更新失败, 可能有重复车牌");
    }
}
