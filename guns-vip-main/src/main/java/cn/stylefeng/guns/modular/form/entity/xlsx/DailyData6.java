package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.*;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @Author: rednoob
 * @Describe: 六工区日计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyData6 {
    //private Integer startId = 1;
    @Excel(name = "序号", orderNum = "1")
    private Integer id;
    @ExcelCollection(name = "第一工作面", orderNum = "2")
    private List<PoundData1> poundData1;
    @ExcelCollection(name = "第二工作面", orderNum = "3")
    private List<PoundData1> poundData2;
    @ExcelCollection(name = "第三工作面", orderNum = "4")
    private List<PoundData1> poundData3;
    /*@ExcelCollection(name = "第四工区" , orderNum = "5")
    private List<PoundData1> poundData4;*/
    @ExcelCollection(name = "第五工作面", orderNum = "6")
    private List<PoundData1> poundData5;
    @ExcelCollection(name = "第六工作面" , orderNum = "7")
    private List<PoundData1> poundData6;
    @ExcelCollection(name = "第七工作面", orderNum = "8")
    private List<PoundData1> poundData7;
}