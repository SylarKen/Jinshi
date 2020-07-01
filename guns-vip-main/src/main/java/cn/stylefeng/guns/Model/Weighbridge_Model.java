package cn.stylefeng.guns.Model;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.io.Serializable;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/9 10:00
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class Weighbridge_Model implements Serializable {
    public Integer id;
    public String weightbridge_code;
    public String name;
    public int type;
    public Decimal topLimit;
    public String ip_meter;
    public String ip_camera;
    public String ip_led;
    public Integer available;
    public Integer is_online;
    public String b1;
    public String b2;
    public String b3;
}
