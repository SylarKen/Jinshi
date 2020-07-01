package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: rednoob
 * @Describe: 流水日报输出所需要的实体类
 */
@Data
public class DailyData implements Serializable {
    //序号,过磅时间,车牌号,所属工区,磅号,毛重,皮重,净重,方量
    @Excel(name = "序号",width = 5, orderNum = "1")
    private Integer id;
    @Excel(name = "过磅时间", width = 20, orderNum = "2", format = "yyyy-MM-dd HH:mm:ss")
    private Date timeWeigh;
    @Excel(name = "车牌号", width = 15, orderNum = "3")
    private String plateNumber;
    @Excel(name = "所属工作面", width = 15, orderNum = "4")
    private String workArea;
    @Excel(name = "磅号", width = 10, orderNum = "4")
    private String weighbridgeCode;
    @Excel(name = "毛重（吨）", width = 20, orderNum = "5")
    private BigDecimal gross;
    @Excel(name = "皮重（吨）", width = 20, orderNum = "6")
    private BigDecimal tare;
    @Excel(name = "净重（吨）", width = 20, orderNum = "7", isStatistics = true)
    private BigDecimal suttle;
    @Excel(name = "方量（m³）", width = 20, orderNum = "8", isStatistics = true)
    private BigDecimal squareQuantity;

    public BigDecimal getSquareQuantity() {
        return getSuttle().divide(new BigDecimal(3.24),0,BigDecimal.
                ROUND_DOWN);
    }
    public BigDecimal getSuttle() {
        return getGross().subtract(getTare());
    }
}
