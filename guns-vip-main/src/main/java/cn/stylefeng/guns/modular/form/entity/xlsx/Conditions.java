package cn.stylefeng.guns.modular.form.entity.xlsx;

import lombok.Data;

import java.util.Date;

/**
 * @Author: rednoob
 * @Describe: 报表查询条件实体类
 */
@Data
public class Conditions {
    private String beginTime;
    private String endTime;
    private String day;
    private String month;
    private String season;
    private String half;
    private String year;
    private String areaCode;
    private String poundNumber;
    private String plateNumber;
}
