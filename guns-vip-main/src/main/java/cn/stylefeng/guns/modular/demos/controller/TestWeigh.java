package cn.stylefeng.guns.modular.demos.controller;

import cn.hutool.core.lang.Console;
import cn.stylefeng.guns.Tools.ControlBox.ControlBox;
import cn.stylefeng.guns.Tools.ControlBox.ControlBoxService;
//import cn.stylefeng.guns.Tools.Led.LedHandler;
import cn.stylefeng.guns.Tools.Led.LedHandler;
import cn.stylefeng.guns.server.bll.model.WeighBridge;
import cn.stylefeng.guns.server.component.WeighBridgeHandler;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/12 9:54
 * @description：
 * @modified By：
 * @version: $
 */
@Controller
@RequestMapping(value = "testweigh/")
public class TestWeigh {

    private String PREFIX = "/demos/testweigh/";

    @RequestMapping("index")
    public String Index() {
        return PREFIX + "index.html";
    }

    @RequestMapping("getweight")
    @ResponseBody
    public Object GetWeight() {
        WeighBridge bridge = WeighBridgeHandler.GetBridge_ByCamera("172.16.11.36");
        WeighBridge bridge2 = WeighBridgeHandler.GetBridge_ByCamera("172.16.11.6");
        BigDecimal w1 = bridge.getWeight();
        BigDecimal w2 = bridge2.getWeight();
//        return "---------";
//        return (w1 == null ? "" : "1") + "------------" + (w2 == null ? "" : "2");
        return (w1 == null ? "" : w1.toString()) + ";  " + (w2 == null ? "" : w2.toString());
    }

    @RequestMapping("sendtest")
    @ResponseBody
    public Object SendTest(HttpServletRequest request) {
        int key = Integer.parseInt(request.getParameter("key"));
        try {
//            ControlBox.Send("192.168.9.109", key);
            ControlBoxService.Send_Up("172.16.11.39");
        } catch (Exception ex) {
        }
        return "true";
    }


    @RequestMapping("sendledtest")
    @ResponseBody
    public Object SendLedTest(HttpServletRequest request) {
        String ip = request.getParameter("ip");
        String text = request.getParameter("text");
        try {
//            ControlBox.Send("192.168.9.109", key);
//            LedHandler.SendMessage("192.168.1.101", text);
            LedHandler.SendMessage(ip, text);
//            LedHandler.SendMessage(1,"我是测试");
//            LedHandler.SendMessage(2,"我是测试");
        } catch (Exception ex) {
            Console.log(ex.getMessage());
        }
        return "true";
    }

    @RequestMapping("valuetest")
    @ResponseBody
    public Object ValueTest(HttpServletRequest request) {
        BigDecimal a = new BigDecimal(234);
        System.out.println(a);
        BigDecimal b = a;
        System.out.println(b);
        a = a.divide(new BigDecimal(2));
        System.out.println(a);
        System.out.println(b);
        return "true";
    }
}
