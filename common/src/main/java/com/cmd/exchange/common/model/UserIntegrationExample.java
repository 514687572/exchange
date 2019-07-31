package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserIntegrationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserIntegrationExample() {
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

        public Criteria andUserIntegrationIsNull() {
            addCriterion("user_integration is null");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationIsNotNull() {
            addCriterion("user_integration is not null");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationEqualTo(BigDecimal value) {
            addCriterion("user_integration =", value, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationNotEqualTo(BigDecimal value) {
            addCriterion("user_integration <>", value, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationGreaterThan(BigDecimal value) {
            addCriterion("user_integration >", value, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("user_integration >=", value, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationLessThan(BigDecimal value) {
            addCriterion("user_integration <", value, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationLessThanOrEqualTo(BigDecimal value) {
            addCriterion("user_integration <=", value, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationIn(List<BigDecimal> values) {
            addCriterion("user_integration in", values, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationNotIn(List<BigDecimal> values) {
            addCriterion("user_integration not in", values, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("user_integration between", value1, value2, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andUserIntegrationNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("user_integration not between", value1, value2, "userIntegration");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeIsNull() {
            addCriterion("integration_type is null");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeIsNotNull() {
            addCriterion("integration_type is not null");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeEqualTo(Integer value) {
            addCriterion("integration_type =", value, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeNotEqualTo(Integer value) {
            addCriterion("integration_type <>", value, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeGreaterThan(Integer value) {
            addCriterion("integration_type >", value, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("integration_type >=", value, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeLessThan(Integer value) {
            addCriterion("integration_type <", value, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("integration_type <=", value, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeIn(List<Integer> values) {
            addCriterion("integration_type in", values, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeNotIn(List<Integer> values) {
            addCriterion("integration_type not in", values, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeBetween(Integer value1, Integer value2) {
            addCriterion("integration_type between", value1, value2, "integrationType");
            return (Criteria) this;
        }

        public Criteria andIntegrationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("integration_type not between", value1, value2, "integrationType");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
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