package cn.stylefeng.guns.modular.form.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.form.entity.Vehicle;
import cn.stylefeng.guns.modular.form.entity.VehicleExample;
import cn.stylefeng.guns.modular.form.mapper.VehicleMapper;
import cn.stylefeng.guns.modular.form.service.VehicleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: rednoob
 * @Describe: 车辆业务层
 */
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {

    @Autowired
    private VehicleMapper dao;

    @Override
    public Page queryVehicle(String plateNumber, String vehicleType, String workArea) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.queryVehicle(page, plateNumber, vehicleType, workArea);
    }

    @Override
    @Transactional
    public Boolean deleteVehicleById(Long carId) {
        if (carId == null) return false;
        return this.dao.deleteByPrimaryKey(carId.intValue()) == 1;
    }

    @Override
    public Vehicle getInfo(Long id) {
        if (id == null) return null;
        return this.dao.selectByPrimaryKey(id.intValue());
    }

    @Override
    @Transactional
    public Boolean updateByDetail(Vehicle car) {
        String plateNumber = car.getPlateNumber();
        VehicleExample e = new VehicleExample();
        e.createCriteria().andPlateNumberEqualTo(plateNumber);
        List<Vehicle> vehicles = this.dao.selectByExample(e);
        if (vehicles.size() != 0) {
            Vehicle vehicle = vehicles.get(0);
            if (car.getId() != vehicle.getId()) {
                return false;
            }
        }
        if (car == null) return false;
        return this.dao.updateByPrimaryKeySelective(car) == 1;
    }

    @Override
    public Boolean addVehicle(Vehicle car) {
        if (car == null) return false;
        String plateNumber = car.getPlateNumber();
        VehicleExample e = new VehicleExample();
        e.createCriteria().andPlateNumberEqualTo(plateNumber);
        List<Vehicle> vehicles = this.dao.selectByExample(e);
        if (vehicles.size() != 0) {
            return false;
        }
        return this.dao.insertSelective(car) == 1;
    }
}
