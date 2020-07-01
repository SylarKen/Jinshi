package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: rednoob
 * @Describe: 各个运输车辆xlsx实体类
 */
@Data
public class EveryCarsXlsxData {

    @Excel(name = "序号",width = 5, orderNum = "1")
    private Integer id;
    @Excel(name = "过磅月份", width = 20, orderNum = "2", exportFormat = "yyyy-MM")
    private Date month;
    @Excel(name = "过磅日期", width = 20, orderNum = "3", exportFormat = "dd")
    private Date day;
    @Excel(name = "过磅时间", width = 20, orderNum = "4", exportFormat = "HH:mm:ss")
    private Date time;
    @Excel(name = "磅号", width = 20, orderNum = "5")
    private String weighbridgeCode;

    public BigDecimal getSquareQuantity() {
        return getSuttle().divide(new BigDecimal(3.24), 0, BigDecimal.
                ROUND_DOWN);
    }

    public BigDecimal getSuttle() {
        return getGross().subtract(getTare());
    }

    @ExcelIgnore
    private BigDecimal tare;
    @Excel(name = "毛重", width = 20, orderNum = "5")
    private BigDecimal gross;
    @Excel(name = "净重（吨）", width = 20, orderNum = "6", isStatistics = true)
    private BigDecimal suttle;
    @Excel(name = "方量（m³）", width = 20, orderNum = "7", isStatistics = true)
    private BigDecimal squareQuantity;
}