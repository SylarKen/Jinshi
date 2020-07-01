package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: rednoob
 * @Describe: 六工区磅的实体类
 *              净重（吨）,方量（m³）
 */
@Data
public class PoundData1 {

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

    @ExcelIgnore
    private BigDecimal tare;
    @ExcelIgnore
    private BigDecimal gross;
}
