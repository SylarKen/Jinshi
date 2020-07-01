package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: rednoob
 * @Describe: 实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedString {
    @Excel(name = "序号")
    private Integer value;
}
