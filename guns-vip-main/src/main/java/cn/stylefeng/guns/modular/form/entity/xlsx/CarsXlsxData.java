package cn.stylefeng.guns.modular.form.entity.xlsx;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: rednoob
 * @Describe: 运输车辆xlsx实体类
 */
@Data
public class CarsXlsxData {

    @Excel(name = "工作面", width = 25, orderNum = "1")
    private String workArea;
    @Excel(name = "车牌号", width = 25, orderNum = "2")
    private String plateNumber;
    @Excel(name = "型号", width = 25, orderNum = "3")
    private String vehicleType;
    @Excel(name = "皮重", width = 25, orderNum = "4")
    private BigDecimal tare;
}
