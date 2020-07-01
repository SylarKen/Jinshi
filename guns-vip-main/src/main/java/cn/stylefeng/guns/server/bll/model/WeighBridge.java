package cn.stylefeng.guns.server.bll.model;

import cn.hutool.core.lang.Console;
import cn.stylefeng.guns.Tools.Led.LedHandler;
import cn.stylefeng.guns.modular.form.entity.WeighbridgeConfig;
import cn.stylefeng.guns.modular.form.entity.WeightResult;
import lombok.Data;
import lombok.Synchronized;
import onbon.bx05.Bx5GException;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/12 15:55
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class WeighBridge {
    private WeighbridgeConfig config;
    private BigDecimal weight;
    private BigDecimal weight_last;
    private BigDecimal threshold = new BigDecimal(100);
    private boolean stable;
    private Deque<BigDecimal> weight_list = new LinkedList<BigDecimal>();

    public WeighBridge(WeighbridgeConfig c) {
        this.config = c;
    }

    //    @Synchronized
    public void SetWeight(BigDecimal w) {
        if (w != null) {
            this.weight = w;
            if (this.weight_last != null) {
                if (this.weight_last.compareTo(threshold) >= 0 && this.weight.compareTo(threshold) < 0) {
                    try {
                        LedHandler.SendMessage(this.config.getIpLed(), "请上磅");
                    } catch (Bx5GException e) {
                        e.printStackTrace();
                    }
                }
            }
            CheckStable(w);
            this.weight_last = this.weight;
        }
    }

    //    @Synchronized
    private void CheckStable(BigDecimal w) {
        if (weight_list.size() == 0) {
            weight_list.offer(w);
        } else {
            BigDecimal w_last = weight_list.getLast();
            //if (Math.abs(w.doubleValue() - w_last.doubleValue()) > 200) {
            if (w.doubleValue() < 500 || Math.abs(w.doubleValue() - w_last.doubleValue()) > 100) {
                weight_list.clear();
                this.stable = false;
            } else {
            }
            weight_list.offer(w);
            if (weight_list.size() > 15) {
                this.stable = true;
                weight_list.pollFirst();
            } else {
                this.stable = false;
            }
        }
//        Console.log(this.weight + "-----" + this.stable);
    }

    public WeightResult GetStableWeight() {

        BigDecimal w = this.weight;
        Boolean res = true;
        long startTime = System.currentTimeMillis();
        //int count = 0;
        try {
            if (!stable) {
                do {
                    w = this.weight;
                    //count++;
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime > 5000) {
                        res = false;
                        break;
                    }
                    Thread.sleep(50);
                } while (!stable);
            }

        } catch (Exception e) {
            Console.log(e.getMessage());
        } finally {
            WeightResult weightResult = new WeightResult();
            weightResult.setResult(res);
            weightResult.setValues(w);
            return weightResult;
        }
//        return BigDecimal.valueOf(50l);

    }

}
