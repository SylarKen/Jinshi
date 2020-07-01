package cn.stylefeng.guns.modular.form.mapper;

import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import cn.stylefeng.guns.modular.form.entity.WeighRecordExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.stylefeng.guns.modular.form.entity.xlsx.Conditions;
import cn.stylefeng.guns.modular.form.entity.xlsx.XlsxDataBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.parameters.P;

public interface WeighRecordMapper extends BaseMapper<WeighRecord> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int countByExample(WeighRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int deleteByExample(WeighRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int insert(WeighRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int insertSelective(WeighRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    List<WeighRecord> selectByExampleWithRowbounds(WeighRecordExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    List<WeighRecord> selectByExample(WeighRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    WeighRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int updateByExampleSelective(@Param("record") WeighRecord record, @Param("example") WeighRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int updateByExample(@Param("record") WeighRecord record, @Param("example") WeighRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int updateByPrimaryKeySelective(WeighRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    int updateByPrimaryKey(WeighRecord record);

    Page<Map<String, Object>> queryWeighRecord(@Param("page") Page page, @Param("condition") String condition);

    List<XlsxDataBean> getReportByMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaCode") String areaCode);

    List<XlsxDataBean> getReportBySeason(@Param("date") String date, @Param("areaCode") String areaCode);

    List<XlsxDataBean> getReportByHalf(@Param("areaCode") String areaCode, @Param("date") String date);

    List<XlsxDataBean> getReportByYear(@Param("date") Date date, @Param("areaCode") String areaCode);

    List<WeighRecord> getSixReportByDay(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("poundNumber") String poundNumber);

    List<XlsxDataBean> getSixReportByMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("poundNumber") String poundNumber);

    List<XlsxDataBean> getSixReportBySeason(@Param("date") String date, @Param("poundNumber") String poundNumber);

    List<XlsxDataBean> getSixReportByHalf(@Param("date") String date, @Param("poundNumber") String poundNumber);

    List<XlsxDataBean> getSixReportByYear(@Param("date") String date, @Param("poundNumber") String poundNumber);

    Page<Map<String, Object>> queryListByCondition(@Param("page") Page page,@Param("beginTime") String beginTime,@Param("endTime") String endTime, @Param("day") String day, @Param("month") String month, @Param("season") String season, @Param("half") String half, @Param("year") String year, @Param("areaCode") String areaCode, @Param("poundNumber") String poundNumber, @Param("plateNumber") String plateNumber);

    WeighRecord queryWeighRecordByLicense(@Param("license") String license);
}