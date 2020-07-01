package cn.stylefeng.guns.modular.form.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Table: t_vehicle
 */
@Data
public class Vehicle implements Serializable {
    /**
     * Table:     t_vehicle
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 车牌号
     *
     * Table:     t_vehicle
     * Column:    plate_number
     * Nullable:  false
     */
    private String plateNumber;

    /**
     * 皮重
     *
     * Table:     t_vehicle
     * Column:    tare
     * Nullable:  true
     */
    private BigDecimal tare;

    /**
     * 载重
     *
     * Table:     t_vehicle
     * Column:    load
     * Nullable:  true
     */
    private BigDecimal loads;

    /**
     * 车型：
     *
     * Table:     t_vehicle
     * Column:    vehicle_type
     * Nullable:  true
     */
    private Integer vehicleType;

    /**
     * 备用
     */
    private String b1;

    /**
     * 备用
     */
    private String b2;

    /**
     * 备用
     */
    private String b3;

    /**
     * 工区
     */
    private String workArea;
    /**
     * 白名单
     */
    private String status;

    private static final long serialVersionUID = 1L;
}