package com.yino.drudgery.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobParamInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    protected Integer limitStart;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    protected Integer limitEnd;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public JobParamInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
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
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public Integer getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
     */
    public Integer getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
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

        public Criteria andJobParamUidIsNull() {
            addCriterion("job_param_uid is null");
            return (Criteria) this;
        }

        public Criteria andJobParamUidIsNotNull() {
            addCriterion("job_param_uid is not null");
            return (Criteria) this;
        }

        public Criteria andJobParamUidEqualTo(String value) {
            addCriterion("job_param_uid =", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidNotEqualTo(String value) {
            addCriterion("job_param_uid <>", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidGreaterThan(String value) {
            addCriterion("job_param_uid >", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidGreaterThanOrEqualTo(String value) {
            addCriterion("job_param_uid >=", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidLessThan(String value) {
            addCriterion("job_param_uid <", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidLessThanOrEqualTo(String value) {
            addCriterion("job_param_uid <=", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidLike(String value) {
            addCriterion("job_param_uid like", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidNotLike(String value) {
            addCriterion("job_param_uid not like", value, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidIn(List<String> values) {
            addCriterion("job_param_uid in", values, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidNotIn(List<String> values) {
            addCriterion("job_param_uid not in", values, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidBetween(String value1, String value2) {
            addCriterion("job_param_uid between", value1, value2, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidNotBetween(String value1, String value2) {
            addCriterion("job_param_uid not between", value1, value2, "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andRowStatusIsNull() {
            addCriterion("row_status is null");
            return (Criteria) this;
        }

        public Criteria andRowStatusIsNotNull() {
            addCriterion("row_status is not null");
            return (Criteria) this;
        }

        public Criteria andRowStatusEqualTo(Byte value) {
            addCriterion("row_status =", value, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusNotEqualTo(Byte value) {
            addCriterion("row_status <>", value, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusGreaterThan(Byte value) {
            addCriterion("row_status >", value, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("row_status >=", value, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusLessThan(Byte value) {
            addCriterion("row_status <", value, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusLessThanOrEqualTo(Byte value) {
            addCriterion("row_status <=", value, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusIn(List<Byte> values) {
            addCriterion("row_status in", values, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusNotIn(List<Byte> values) {
            addCriterion("row_status not in", values, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusBetween(Byte value1, Byte value2) {
            addCriterion("row_status between", value1, value2, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("row_status not between", value1, value2, "rowStatus");
            return (Criteria) this;
        }

        public Criteria andRowVersionIsNull() {
            addCriterion("row_version is null");
            return (Criteria) this;
        }

        public Criteria andRowVersionIsNotNull() {
            addCriterion("row_version is not null");
            return (Criteria) this;
        }

        public Criteria andRowVersionEqualTo(Date value) {
            addCriterion("row_version =", value, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionNotEqualTo(Date value) {
            addCriterion("row_version <>", value, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionGreaterThan(Date value) {
            addCriterion("row_version >", value, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionGreaterThanOrEqualTo(Date value) {
            addCriterion("row_version >=", value, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionLessThan(Date value) {
            addCriterion("row_version <", value, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionLessThanOrEqualTo(Date value) {
            addCriterion("row_version <=", value, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionIn(List<Date> values) {
            addCriterion("row_version in", values, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionNotIn(List<Date> values) {
            addCriterion("row_version not in", values, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionBetween(Date value1, Date value2) {
            addCriterion("row_version between", value1, value2, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andRowVersionNotBetween(Date value1, Date value2) {
            addCriterion("row_version not between", value1, value2, "rowVersion");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNull() {
            addCriterion("param_type is null");
            return (Criteria) this;
        }

        public Criteria andParamTypeIsNotNull() {
            addCriterion("param_type is not null");
            return (Criteria) this;
        }

        public Criteria andParamTypeEqualTo(String value) {
            addCriterion("param_type =", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotEqualTo(String value) {
            addCriterion("param_type <>", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThan(String value) {
            addCriterion("param_type >", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeGreaterThanOrEqualTo(String value) {
            addCriterion("param_type >=", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThan(String value) {
            addCriterion("param_type <", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLessThanOrEqualTo(String value) {
            addCriterion("param_type <=", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeLike(String value) {
            addCriterion("param_type like", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotLike(String value) {
            addCriterion("param_type not like", value, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeIn(List<String> values) {
            addCriterion("param_type in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotIn(List<String> values) {
            addCriterion("param_type not in", values, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeBetween(String value1, String value2) {
            addCriterion("param_type between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamTypeNotBetween(String value1, String value2) {
            addCriterion("param_type not between", value1, value2, "paramType");
            return (Criteria) this;
        }

        public Criteria andParamKeyIsNull() {
            addCriterion("param_key is null");
            return (Criteria) this;
        }

        public Criteria andParamKeyIsNotNull() {
            addCriterion("param_key is not null");
            return (Criteria) this;
        }

        public Criteria andParamKeyEqualTo(String value) {
            addCriterion("param_key =", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotEqualTo(String value) {
            addCriterion("param_key <>", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyGreaterThan(String value) {
            addCriterion("param_key >", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyGreaterThanOrEqualTo(String value) {
            addCriterion("param_key >=", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyLessThan(String value) {
            addCriterion("param_key <", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyLessThanOrEqualTo(String value) {
            addCriterion("param_key <=", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyLike(String value) {
            addCriterion("param_key like", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotLike(String value) {
            addCriterion("param_key not like", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyIn(List<String> values) {
            addCriterion("param_key in", values, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotIn(List<String> values) {
            addCriterion("param_key not in", values, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyBetween(String value1, String value2) {
            addCriterion("param_key between", value1, value2, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotBetween(String value1, String value2) {
            addCriterion("param_key not between", value1, value2, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNull() {
            addCriterion("param_value is null");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNotNull() {
            addCriterion("param_value is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueEqualTo(String value) {
            addCriterion("param_value =", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotEqualTo(String value) {
            addCriterion("param_value <>", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThan(String value) {
            addCriterion("param_value >", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThanOrEqualTo(String value) {
            addCriterion("param_value >=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThan(String value) {
            addCriterion("param_value <", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThanOrEqualTo(String value) {
            addCriterion("param_value <=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLike(String value) {
            addCriterion("param_value like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotLike(String value) {
            addCriterion("param_value not like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueIn(List<String> values) {
            addCriterion("param_value in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotIn(List<String> values) {
            addCriterion("param_value not in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueBetween(String value1, String value2) {
            addCriterion("param_value between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotBetween(String value1, String value2) {
            addCriterion("param_value not between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidIsNull() {
            addCriterion("job_basic_uid is null");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidIsNotNull() {
            addCriterion("job_basic_uid is not null");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidEqualTo(String value) {
            addCriterion("job_basic_uid =", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidNotEqualTo(String value) {
            addCriterion("job_basic_uid <>", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidGreaterThan(String value) {
            addCriterion("job_basic_uid >", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidGreaterThanOrEqualTo(String value) {
            addCriterion("job_basic_uid >=", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidLessThan(String value) {
            addCriterion("job_basic_uid <", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidLessThanOrEqualTo(String value) {
            addCriterion("job_basic_uid <=", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidLike(String value) {
            addCriterion("job_basic_uid like", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidNotLike(String value) {
            addCriterion("job_basic_uid not like", value, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidIn(List<String> values) {
            addCriterion("job_basic_uid in", values, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidNotIn(List<String> values) {
            addCriterion("job_basic_uid not in", values, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidBetween(String value1, String value2) {
            addCriterion("job_basic_uid between", value1, value2, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidNotBetween(String value1, String value2) {
            addCriterion("job_basic_uid not between", value1, value2, "jobBasicUid");
            return (Criteria) this;
        }

        public Criteria andJobParamUidLikeInsensitive(String value) {
            addCriterion("upper(job_param_uid) like", value.toUpperCase(), "jobParamUid");
            return (Criteria) this;
        }

        public Criteria andParamTypeLikeInsensitive(String value) {
            addCriterion("upper(param_type) like", value.toUpperCase(), "paramType");
            return (Criteria) this;
        }

        public Criteria andParamKeyLikeInsensitive(String value) {
            addCriterion("upper(param_key) like", value.toUpperCase(), "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamValueLikeInsensitive(String value) {
            addCriterion("upper(param_value) like", value.toUpperCase(), "paramValue");
            return (Criteria) this;
        }

        public Criteria andJobBasicUidLikeInsensitive(String value) {
            addCriterion("upper(job_basic_uid) like", value.toUpperCase(), "jobBasicUid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table job_param_info
     *
     * @mbggenerated do_not_delete_during_merge Mon Apr 17 17:17:05 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table job_param_info
     *
     * @mbggenerated Mon Apr 17 17:17:05 CST 2017
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