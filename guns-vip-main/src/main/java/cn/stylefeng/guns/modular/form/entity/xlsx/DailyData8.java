package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: rednoob
 * @Describe: 六工作面季计实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyData8 {
    //private Integer startId = 1;
    @Excel(name = "日期", orderNum = "1", width = 20, needMerge = true)
    private String days;
    @ExcelCollection(name = "第一工作面", orderNum = "2")
    private List<PoundData> poundData1;
    @ExcelCollection(name = "第二工作面", orderNum = "3")
    private List<PoundData> poundData2;
    @ExcelCollection(name = "第三工作面", orderNum = "4")
    private List<PoundData> poundData3;
    /*@ExcelCollection(name = "第四工作面" , orderNum = "5")
    private List<PoundData> poundData4;*/
    @ExcelCollection(name = "第五工作面", orderNum = "6")
    private List<PoundData> poundData5;
    @ExcelCollection(name = "第六工作面" ,orderNum = "7")
    private List<PoundData> poundData6;
    @ExcelCollection(name = "第七工作面",orderNum = "8")
    private List<PoundData> poundData7;
    @ExcelCollection(name = "小计",orderNum = "8")
    private List<PoundData> poundData8;
}