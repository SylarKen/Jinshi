package cn.stylefeng.guns.modular.form.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: rednoob
 * @Describe: 工区实体类
 */
@Data
public class WorkArea {
    /**
     * 主键
     */
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    /**
     * 工区名字
     */
    private String workArea;
}
