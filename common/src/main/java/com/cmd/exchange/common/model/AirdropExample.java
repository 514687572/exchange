package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AirdropExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AirdropExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinIsNull() {
            addCriterion("integration_min is null");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinIsNotNull() {
            addCriterion("integration_min is not null");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinEqualTo(BigDecimal value) {
            addCriterion("integration_min =", value, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinNotEqualTo(BigDecimal value) {
            addCriterion("integration_min <>", value, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinGreaterThan(BigDecimal value) {
            addCriterion("integration_min >", value, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("integration_min >=", value, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinLessThan(BigDecimal value) {
            addCriterion("integration_min <", value, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinLessThanOrEqualTo(BigDecimal value) {
            addCriterion("integration_min <=", value, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinIn(List<BigDecimal> values) {
            addCriterion("integration_min in", values, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinNotIn(List<BigDecimal> values) {
            addCriterion("integration_min not in", values, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integration_min between", value1, value2, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMinNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integration_min not between", value1, value2, "integrationMin");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxIsNull() {
            addCriterion("integration_max is null");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxIsNotNull() {
            addCriterion("integration_max is not null");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxEqualTo(BigDecimal value) {
            addCriterion("integration_max =", value, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxNotEqualTo(BigDecimal value) {
            addCriterion("integration_max <>", value, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxGreaterThan(BigDecimal value) {
            addCriterion("integration_max >", value, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("integration_max >=", value, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxLessThan(BigDecimal value) {
            addCriterion("integration_max <", value, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("integration_max <=", value, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxIn(List<BigDecimal> values) {
            addCriterion("integration_max in", values, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxNotIn(List<BigDecimal> values) {
            addCriterion("integration_max not in", values, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integration_max between", value1, value2, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andIntegrationMaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integration_max not between", value1, value2, "integrationMax");
            return (Criteria) this;
        }

        public Criteria andAirdropNumIsNull() {
            addCriterion("airdrop_num is null");
            return (Criteria) this;
        }

        public Criteria andAirdropNumIsNotNull() {
            addCriterion("airdrop_num is not null");
            return (Criteria) this;
        }

        public Criteria andAirdropNumEqualTo(BigDecimal value) {
            addCriterion("airdrop_num =", value, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumNotEqualTo(BigDecimal value) {
            addCriterion("airdrop_num <>", value, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumGreaterThan(BigDecimal value) {
            addCriterion("airdrop_num >", value, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("airdrop_num >=", value, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumLessThan(BigDecimal value) {
            addCriterion("airdrop_num <", value, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("airdrop_num <=", value, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumIn(List<BigDecimal> values) {
            addCriterion("airdrop_num in", values, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumNotIn(List<BigDecimal> values) {
            addCriterion("airdrop_num not in", values, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("airdrop_num between", value1, value2, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andAirdropNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("airdrop_num not between", value1, value2, "airdropNum");
            return (Criteria) this;
        }

        public Criteria andDoTimeIsNull() {
            addCriterion("do_time is null");
            return (Criteria) this;
        }

        public Criteria andDoTimeIsNotNull() {
            addCriterion("do_time is not null");
            return (Criteria) this;
        }

        public Criteria andDoTimeEqualTo(Date value) {
            addCriterion("do_time =", value, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeNotEqualTo(Date value) {
            addCriterion("do_time <>", value, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeGreaterThan(Date value) {
            addCriterion("do_time >", value, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("do_time >=", value, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeLessThan(Date value) {
            addCriterion("do_time <", value, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeLessThanOrEqualTo(Date value) {
            addCriterion("do_time <=", value, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeIn(List<Date> values) {
            addCriterion("do_time in", values, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeNotIn(List<Date> values) {
            addCriterion("do_time not in", values, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeBetween(Date value1, Date value2) {
            addCriterion("do_time between", value1, value2, "doTime");
            return (Criteria) this;
        }

        public Criteria andDoTimeNotBetween(Date value1, Date value2) {
            addCriterion("do_time not between", value1, value2, "doTime");
            return (Criteria) this;
        }

        public Criteria andUserNumIsNull() {
            addCriterion("user_num is null");
            return (Criteria) this;
        }

        public Criteria andUserNumIsNotNull() {
            addCriterion("user_num is not null");
            return (Criteria) this;
        }

        public Criteria andUserNumEqualTo(Integer value) {
            addCriterion("user_num =", value, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumNotEqualTo(Integer value) {
            addCriterion("user_num <>", value, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumGreaterThan(Integer value) {
            addCriterion("user_num >", value, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_num >=", value, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumLessThan(Integer value) {
            addCriterion("user_num <", value, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumLessThanOrEqualTo(Integer value) {
            addCriterion("user_num <=", value, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumIn(List<Integer> values) {
            addCriterion("user_num in", values, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumNotIn(List<Integer> values) {
            addCriterion("user_num not in", values, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumBetween(Integer value1, Integer value2) {
            addCriterion("user_num between", value1, value2, "userNum");
            return (Criteria) this;
        }

        public Criteria andUserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("user_num not between", value1, value2, "userNum");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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