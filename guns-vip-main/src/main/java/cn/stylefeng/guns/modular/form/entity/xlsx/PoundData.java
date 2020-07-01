package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: rednoob
 * @Describe: 磅的实体类
 *              车数,重量（吨）,方量（m³）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoundData {

    @Excel(name = "车数", orderNum = "1",width = 5,  isStatistics = true)
    private Integer countCars;
    @Excel(name = "重量（吨）", orderNum = "2", width = 10, isStatistics = true)
    private BigDecimal weight;
    @Excel(name = "方量（m³）", orderNum = "3", width = 10, isStatistics = true)
    private BigDecimal squareQuantity;

    public BigDecimal getSquareQuantity() {
        return getWeight().divide(new BigDecimal(3.24),0,BigDecimal.
                ROUND_DOWN);
    }

    public BigDecimal getWeight() {
        return getGross().subtract(getTare());
    }

    @ExcelIgnore
    private BigDecimal tare;

    public PoundData(Integer countCars, BigDecimal tare, BigDecimal gross) {
        this.countCars = countCars;
        this.tare = tare;
        this.gross = gross;
    }

    @ExcelIgnore
    private BigDecimal gross;

}
