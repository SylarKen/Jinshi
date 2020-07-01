package cn.stylefeng.guns.modular.form.service;

import cn.stylefeng.guns.modular.form.entity.Vehicle;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @Author: rednoob
 * @Describe:
 */
public interface VehicleService {

    /**
     * 查询车辆
     */
    Page<Map<String, Object>>  queryVehicle(String plateNumber, String vehicleType, String workArea);

    /**
     * 删除车辆
     * @param carId
     */
    Boolean deleteVehicleById(Long carId);

    /**
     * 查询车辆信息
     * @param id
     */
    Vehicle getInfo(Long id);

    /**
     * 更新车辆信息
     * @param car
     */
    Boolean updateByDetail(Vehicle car);

    /**
     * 添加车辆信息
     * @param vehicle
     */
    Boolean addVehicle(Vehicle vehicle);
}
