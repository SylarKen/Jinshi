package cn.stylefeng.guns.modular.form.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeighRecordExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public WeighRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPlateNumberIsNull() {
            addCriterion("plate_number is null");
            return (Criteria) this;
        }

        public Criteria andPlateNumberIsNotNull() {
            addCriterion("plate_number is not null");
            return (Criteria) this;
        }

        public Criteria andPlateNumberEqualTo(String value) {
            addCriterion("plate_number =", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberNotEqualTo(String value) {
            addCriterion("plate_number <>", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberGreaterThan(String value) {
            addCriterion("plate_number >", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberGreaterThanOrEqualTo(String value) {
            addCriterion("plate_number >=", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberLessThan(String value) {
            addCriterion("plate_number <", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberLessThanOrEqualTo(String value) {
            addCriterion("plate_number <=", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberLike(String value) {
            addCriterion("plate_number like", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberNotLike(String value) {
            addCriterion("plate_number not like", value, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberIn(List<String> values) {
            addCriterion("plate_number in", values, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberNotIn(List<String> values) {
            addCriterion("plate_number not in", values, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberBetween(String value1, String value2) {
            addCriterion("plate_number between", value1, value2, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andPlateNumberNotBetween(String value1, String value2) {
            addCriterion("plate_number not between", value1, value2, "plateNumber");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeIsNull() {
            addCriterion("weighbridge_code is null");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeIsNotNull() {
            addCriterion("weighbridge_code is not null");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeEqualTo(String value) {
            addCriterion("weighbridge_code =", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeNotEqualTo(String value) {
            addCriterion("weighbridge_code <>", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeGreaterThan(String value) {
            addCriterion("weighbridge_code >", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("weighbridge_code >=", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeLessThan(String value) {
            addCriterion("weighbridge_code <", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeLessThanOrEqualTo(String value) {
            addCriterion("weighbridge_code <=", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeLike(String value) {
            addCriterion("weighbridge_code like", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeNotLike(String value) {
            addCriterion("weighbridge_code not like", value, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeIn(List<String> values) {
            addCriterion("weighbridge_code in", values, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeNotIn(List<String> values) {
            addCriterion("weighbridge_code not in", values, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeBetween(String value1, String value2) {
            addCriterion("weighbridge_code between", value1, value2, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andWeighbridgeCodeNotBetween(String value1, String value2) {
            addCriterion("weighbridge_code not between", value1, value2, "weighbridgeCode");
            return (Criteria) this;
        }

        public Criteria andTareIsNull() {
            addCriterion("tare is null");
            return (Criteria) this;
        }

        public Criteria andTareIsNotNull() {
            addCriterion("tare is not null");
            return (Criteria) this;
        }

        public Criteria andTareEqualTo(BigDecimal value) {
            addCriterion("tare =", value, "tare");
            return (Criteria) this;
        }

        public Criteria andTareNotEqualTo(BigDecimal value) {
            addCriterion("tare <>", value, "tare");
            return (Criteria) this;
        }

        public Criteria andTareGreaterThan(BigDecimal value) {
            addCriterion("tare >", value, "tare");
            return (Criteria) this;
        }

        public Criteria andTareGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tare >=", value, "tare");
            return (Criteria) this;
        }

        public Criteria andTareLessThan(BigDecimal value) {
            addCriterion("tare <", value, "tare");
            return (Criteria) this;
        }

        public Criteria andTareLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tare <=", value, "tare");
            return (Criteria) this;
        }

        public Criteria andTareIn(List<BigDecimal> values) {
            addCriterion("tare in", values, "tare");
            return (Criteria) this;
        }

        public Criteria andTareNotIn(List<BigDecimal> values) {
            addCriterion("tare not in", values, "tare");
            return (Criteria) this;
        }

        public Criteria andTareBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tare between", value1, value2, "tare");
            return (Criteria) this;
        }

        public Criteria andTareNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tare not between", value1, value2, "tare");
            return (Criteria) this;
        }

        public Criteria andGrossIsNull() {
            addCriterion("gross is null");
            return (Criteria) this;
        }

        public Criteria andGrossIsNotNull() {
            addCriterion("gross is not null");
            return (Criteria) this;
        }

        public Criteria andGrossEqualTo(BigDecimal value) {
            addCriterion("gross =", value, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossNotEqualTo(BigDecimal value) {
            addCriterion("gross <>", value, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossGreaterThan(BigDecimal value) {
            addCriterion("gross >", value, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gross >=", value, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossLessThan(BigDecimal value) {
            addCriterion("gross <", value, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gross <=", value, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossIn(List<BigDecimal> values) {
            addCriterion("gross in", values, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossNotIn(List<BigDecimal> values) {
            addCriterion("gross not in", values, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross between", value1, value2, "gross");
            return (Criteria) this;
        }

        public Criteria andGrossNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross not between", value1, value2, "gross");
            return (Criteria) this;
        }

        public Criteria andTimeWeighIsNull() {
            addCriterion("time_weigh is null");
            return (Criteria) this;
        }

        public Criteria andTimeWeighIsNotNull() {
            addCriterion("time_weigh is not null");
            return (Criteria) this;
        }

        public Criteria andTimeWeighEqualTo(Date value) {
            addCriterion("time_weigh =", value, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighNotEqualTo(Date value) {
            addCriterion("time_weigh <>", value, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighGreaterThan(Date value) {
            addCriterion("time_weigh >", value, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighGreaterThanOrEqualTo(Date value) {
            addCriterion("time_weigh >=", value, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighLessThan(Date value) {
            addCriterion("time_weigh <", value, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighLessThanOrEqualTo(Date value) {
            addCriterion("time_weigh <=", value, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighIn(List<Date> values) {
            addCriterion("time_weigh in", values, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighNotIn(List<Date> values) {
            addCriterion("time_weigh not in", values, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighBetween(Date value1, Date value2) {
            addCriterion("time_weigh between", value1, value2, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andTimeWeighNotBetween(Date value1, Date value2) {
            addCriterion("time_weigh not between", value1, value2, "timeWeigh");
            return (Criteria) this;
        }

        public Criteria andB1IsNull() {
            addCriterion("b1 is null");
            return (Criteria) this;
        }

        public Criteria andB1IsNotNull() {
            addCriterion("b1 is not null");
            return (Criteria) this;
        }

        public Criteria andB1EqualTo(String value) {
            addCriterion("b1 =", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1NotEqualTo(String value) {
            addCriterion("b1 <>", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1GreaterThan(String value) {
            addCriterion("b1 >", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1GreaterThanOrEqualTo(String value) {
            addCriterion("b1 >=", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1LessThan(String value) {
            addCriterion("b1 <", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1LessThanOrEqualTo(String value) {
            addCriterion("b1 <=", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1Like(String value) {
            addCriterion("b1 like", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1NotLike(String value) {
            addCriterion("b1 not like", value, "b1");
            return (Criteria) this;
        }

        public Criteria andB1In(List<String> values) {
            addCriterion("b1 in", values, "b1");
            return (Criteria) this;
        }

        public Criteria andB1NotIn(List<String> values) {
            addCriterion("b1 not in", values, "b1");
            return (Criteria) this;
        }

        public Criteria andB1Between(String value1, String value2) {
            addCriterion("b1 between", value1, value2, "b1");
            return (Criteria) this;
        }

        public Criteria andB1NotBetween(String value1, String value2) {
            addCriterion("b1 not between", value1, value2, "b1");
            return (Criteria) this;
        }

        public Criteria andB2IsNull() {
            addCriterion("b2 is null");
            return (Criteria) this;
        }

        public Criteria andB2IsNotNull() {
            addCriterion("b2 is not null");
            return (Criteria) this;
        }

        public Criteria andB2EqualTo(String value) {
            addCriterion("b2 =", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2NotEqualTo(String value) {
            addCriterion("b2 <>", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2GreaterThan(String value) {
            addCriterion("b2 >", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2GreaterThanOrEqualTo(String value) {
            addCriterion("b2 >=", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2LessThan(String value) {
            addCriterion("b2 <", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2LessThanOrEqualTo(String value) {
            addCriterion("b2 <=", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2Like(String value) {
            addCriterion("b2 like", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2NotLike(String value) {
            addCriterion("b2 not like", value, "b2");
            return (Criteria) this;
        }

        public Criteria andB2In(List<String> values) {
            addCriterion("b2 in", values, "b2");
            return (Criteria) this;
        }

        public Criteria andB2NotIn(List<String> values) {
            addCriterion("b2 not in", values, "b2");
            return (Criteria) this;
        }

        public Criteria andB2Between(String value1, String value2) {
            addCriterion("b2 between", value1, value2, "b2");
            return (Criteria) this;
        }

        public Criteria andB2NotBetween(String value1, String value2) {
            addCriterion("b2 not between", value1, value2, "b2");
            return (Criteria) this;
        }

        public Criteria andB3IsNull() {
            addCriterion("b3 is null");
            return (Criteria) this;
        }

        public Criteria andB3IsNotNull() {
            addCriterion("b3 is not null");
            return (Criteria) this;
        }

        public Criteria andB3EqualTo(String value) {
            addCriterion("b3 =", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3NotEqualTo(String value) {
            addCriterion("b3 <>", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3GreaterThan(String value) {
            addCriterion("b3 >", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3GreaterThanOrEqualTo(String value) {
            addCriterion("b3 >=", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3LessThan(String value) {
            addCriterion("b3 <", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3LessThanOrEqualTo(String value) {
            addCriterion("b3 <=", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3Like(String value) {
            addCriterion("b3 like", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3NotLike(String value) {
            addCriterion("b3 not like", value, "b3");
            return (Criteria) this;
        }

        public Criteria andB3In(List<String> values) {
            addCriterion("b3 in", values, "b3");
            return (Criteria) this;
        }

        public Criteria andB3NotIn(List<String> values) {
            addCriterion("b3 not in", values, "b3");
            return (Criteria) this;
        }

        public Criteria andB3Between(String value1, String value2) {
            addCriterion("b3 between", value1, value2, "b3");
            return (Criteria) this;
        }

        public Criteria andB3NotBetween(String value1, String value2) {
            addCriterion("b3 not between", value1, value2, "b3");
            return (Criteria) this;
        }

        public Criteria andWorkAreaIsNull() {
            addCriterion("work_area is null");
            return (Criteria) this;
        }

        public Criteria andWorkAreaIsNotNull() {
            addCriterion("work_area is not null");
            return (Criteria) this;
        }

        public Criteria andWorkAreaEqualTo(String value) {
            addCriterion("work_area =", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaNotEqualTo(String value) {
            addCriterion("work_area <>", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaGreaterThan(String value) {
            addCriterion("work_area >", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaGreaterThanOrEqualTo(String value) {
            addCriterion("work_area >=", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaLessThan(String value) {
            addCriterion("work_area <", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaLessThanOrEqualTo(String value) {
            addCriterion("work_area <=", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaLike(String value) {
            addCriterion("work_area like", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaNotLike(String value) {
            addCriterion("work_area not like", value, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaIn(List<String> values) {
            addCriterion("work_area in", values, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaNotIn(List<String> values) {
            addCriterion("work_area not in", values, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaBetween(String value1, String value2) {
            addCriterion("work_area between", value1, value2, "workArea");
            return (Criteria) this;
        }

        public Criteria andWorkAreaNotBetween(String value1, String value2) {
            addCriterion("work_area not between", value1, value2, "workArea");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_weigh_record
     *
     * @mbggenerated do_not_delete_during_merge Thu Apr 30 08:59:01 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_weigh_record
     *
     * @mbggenerated Thu Apr 30 08:59:01 CST 2020
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}