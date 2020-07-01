package cn.stylefeng.guns.modular.form.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.stylefeng.guns.Tools.ControlBox.ControlBoxService;
import cn.stylefeng.guns.Tools.Led.LedHandler;
import cn.stylefeng.guns.config.TcpConfig;
import cn.stylefeng.guns.modular.form.entity.*;
import cn.stylefeng.guns.modular.form.mapper.VehicleMapper;
import cn.stylefeng.guns.modular.form.mapper.WeighRecordMapper;
import cn.stylefeng.guns.modular.form.mapper.WeighbridgeConfigMapper;
import cn.stylefeng.guns.modular.form.service.FlowsService;
import cn.stylefeng.guns.server.bll.model.WeighBridge;
import cn.stylefeng.guns.server.component.WeighBridgeHandler;
import onbon.bx05.Bx5GException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 出库流程业务层
 */
@Service
public class FlowsServiceImpl implements FlowsService {

    @Autowired
    private VehicleMapper vehicleDao;

    @Autowired
    private WeighbridgeConfigMapper weighbridgeConfigDao;

    @Autowired
    private WeighRecordMapper weighRecordDao;

    @Override
    public void handle(Map<String, String> map) throws Bx5GException {
        String license = map.get("license");//车牌
        String ipaddr = map.get("ipaddr");//一体机IP地址

        ApplicationContext context = new AnnotationConfigApplicationContext(TcpConfig.class);
        // 根据 ip 取地磅读数
        WeighBridge bridge = WeighBridgeHandler.GetBridge_ByCamera(ipaddr);// 获取地磅对象
        WeightResult weightResult = bridge.GetStableWeight();// 获取地磅读数
        boolean stable = false;
//        if (!stable) {
//            do {
//                weight = bridge.getWeight();// 获取地磅读数
//                stable = bridge.isStable();
//            } while (!stable);
//        }

        if (weightResult.getResult()) {
            BigDecimal weight = weightResult.getValues();
            Console.log(weight);
            VehicleExample vehicleExample = new VehicleExample();
            VehicleExample.Criteria criteria = vehicleExample.createCriteria();
            criteria.andPlateNumberEqualTo(license);
            List<Vehicle> vehicles = this.vehicleDao.selectByExample(vehicleExample);
            if (CollectionUtil.isNotEmpty(vehicles)) {
                WeighbridgeConfigExample wcExample = new WeighbridgeConfigExample();
                wcExample.createCriteria().andIpCameraEqualTo(ipaddr);
                List<WeighbridgeConfig> weighbridgeConfigs = this.weighbridgeConfigDao.selectByExample(wcExample);
              /*  WeighRecord wr = weighRecordDao.queryWeighRecordByLicense(license);//查询此车牌最近时间的情况T
                Date now = new Date();
                //long betweenSecond= DateUtil.between(now, wr.getTimeWeigh(), DateUnit.SECOND);
            Long second = 0l;
            try {
                second = Long.parseLong(context.getBean("seconds").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (betweenSecond < second) {
                return;
            }*/
                if (CollectionUtil.isNotEmpty(weighbridgeConfigs)) {
                    WeighbridgeConfig wc = weighbridgeConfigs.get(0);
                    Vehicle vehicle = vehicles.get(0);
                    //String ipMeter = wc.getIpMeter();//获取地磅IP地址
                    if ("ENABLE".equals(vehicle.getStatus())) { //白名单车辆直接放行,不需要记录称重记录
                        Console.log("白名单用户,允许通行");
                        LedHandler.SendMessage(bridge.getConfig().getIpLed(), "白名单车辆 允许通行");
                        ControlBoxService.Send_Up(wc.getIpControlbox());
                    } else {
                        WeighRecord weighRecord = new WeighRecord();
                        weighRecord.setPlateNumber(license);
                        weighRecord.setWeighbridgeCode(wc.getWeightbridgeCode());
                        weighRecord.setTare(vehicle.getTare());
                        weighRecord.setGross(weight);
                        weighRecord.setTimeWeigh(new Date());
                        weighRecord.setWorkArea(vehicle.getWorkArea());
                        if (weighRecordDao.insertSelective(weighRecord) == 1) { //插入数据
                            // 发送抬杆指令，将ip替换为 controlbox 的ip
                            //ControlBoxService.Send_Up("192.168.9.20");
                            ControlBoxService.Send_Up(wc.getIpControlbox());
                            LedHandler.SendMessage(bridge.getConfig().getIpLed(), " 称重完毕   请下磅");
                        }
                    }
                }else {
                    Console.log("地磅信息未查询到");
                }
            }else {
                Console.log("未在数据库中查询到车辆信息");
                LedHandler.SendMessage(bridge.getConfig().getIpLed(), "车辆未授权 重新上磅");
            }
        }else {
            Console.log("地磅数据没有接受成功");
            LedHandler.SendMessage(bridge.getConfig().getIpLed(), "   地磅    通讯异常");
        }
    }
}
