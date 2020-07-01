package cn.stylefeng.guns.modular.form.entity.xlsx;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: rednoob
 * @Describe: 接收车辆重量皮重时间实体类
 */
@Data
public class XlsxDataBean {

    private Integer vehiclenumber;
    private BigDecimal tare;
    private BigDecimal gross;
    private Date showTime;
    private String workArea;
    private String months;
    private String weighbridgeCode;

}
