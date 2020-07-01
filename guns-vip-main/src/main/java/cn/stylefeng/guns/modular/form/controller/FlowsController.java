package cn.stylefeng.guns.modular.form.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.modular.form.service.FlowsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import onbon.bx05.Bx5GException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: rednoob
 * @Describe: 过磅流程, 此Controller未有用户权限控制
 */
@Controller
@RequestMapping("/flows")
@Slf4j
public class FlowsController {

    @Autowired
    private FlowsService service;

    /**
     * 接收车牌信息
     */
    @PostMapping("/vehicleData")
    @Transactional
    public void handle(@RequestBody String json) throws Bx5GException {
        Map<String, String> map = new HashMap<>();
        JSONObject jo = JSON.parseObject(json);
        if (jo.containsKey("AlarmInfoPlate")) {
            JSONObject alarmInfoPlate = jo.getJSONObject("AlarmInfoPlate");
            if (alarmInfoPlate.containsKey("ipaddr")) {
                String ipaddr = alarmInfoPlate.getString("ipaddr");
                map.put("ipaddr", ipaddr);
            }
            if (alarmInfoPlate.containsKey("result")) {
                JSONObject result = alarmInfoPlate.getJSONObject("result");
                if (result.containsKey("PlateResult")) {
                    JSONObject plateResult = result.getJSONObject("PlateResult");
                    if (plateResult.containsKey("license")) {
                        String license = plateResult.getString("license");
                        license = license.substring(1);
                        StringBuffer sb = new StringBuffer("金");
                        sb.append(license);
                        sb.insert(2,"·");
                        log.info("车牌:"+sb);
                        map.put("license",sb.toString());
                    }
                }
            }
        }
        if (CollectionUtil.isNotEmpty(map)) {
            this.service.handle(map);
        }
    }

}
