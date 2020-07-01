package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: rednoob
 * @Describe: 工区日报输出所需要的实体类
 */
@Data
public class DailyData2 {
    @Excel(name = "序号",width = 5, orderNum = "1")
    private Integer id;
    @Excel(name = "过磅时间", width = 20, orderNum = "2", format = "yyyy-MM-dd HH-mm-ss")
    private Date timeWeigh;
    @Excel(name = "车牌号", width = 15, orderNum = "3")
    private String plateNumber;
    @Excel(name = "毛重（吨）", width = 20, orderNum = "5")
    private BigDecimal gross;
    @Excel(name = "皮重（吨）", width = 20, orderNum = "6")
    private BigDecimal tare;
    @Excel(name = "净重（吨）", width = 20, orderNum = "6")
    private BigDecimal suttle;
    @Excel(name = "方量（m³）", width = 20, orderNum = "8")
    private BigDecimal squareQuantity;

    public BigDecimal getSquareQuantity() {
        return getSuttle().divide(new BigDecimal(3.24),0,BigDecimal.
                ROUND_DOWN);
    }
    public BigDecimal getSuttle() {
        return getGross().subtract(getTare());
    }
}
