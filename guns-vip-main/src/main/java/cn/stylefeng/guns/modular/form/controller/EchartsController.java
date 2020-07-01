package cn.stylefeng.guns.modular.form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: rednoob
 * @Describe: 所有echarts请求Controller层
 */
@Controller
@RequestMapping("/echarts")
public class EchartsController {
    private String PREFIX = "/echarts";


    /**
     * 返回地磅仪表页面
     */
    @GetMapping("/wbcStatus")
    public String getStatus() {
        return PREFIX + "/status.html";
    }
}
