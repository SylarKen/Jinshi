package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: rednoob
 * @Describe: 工区季计
 */
@Data
public class DailyData4 {

    @Excel(name = "月份", orderNum = "1", width = 20, needMerge = true)
    private String days;
    @ExcelCollection(name = "磅一", orderNum = "2")
    private List<PoundData> poundData1;
    @ExcelCollection(name = "磅二", orderNum = "3")
    private List<PoundData> poundData2;
    @ExcelCollection(name = "小计", orderNum = "4")
    private List<PoundData> poundData3;

}
