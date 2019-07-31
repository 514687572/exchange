package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserContractsExample {
    protected String orderByClause;
    protected Integer limit;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserContractsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

        public Criteria andContractsNameIsNull() {
            addCriterion("contracts_name is null");
            return (Criteria) this;
        }

        public Criteria andContractsNameIsNotNull() {
            addCriterion("contracts_name is not null");
            return (Criteria) this;
        }

        public Criteria andContractsNameEqualTo(String value) {
            addCriterion("contracts_name =", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameNotEqualTo(String value) {
            addCriterion("contracts_name <>", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameGreaterThan(String value) {
            addCriterion("contracts_name >", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameGreaterThanOrEqualTo(String value) {
            addCriterion("contracts_name >=", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameLessThan(String value) {
            addCriterion("contracts_name <", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameLessThanOrEqualTo(String value) {
            addCriterion("contracts_name <=", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameLike(String value) {
            addCriterion("contracts_name like", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameNotLike(String value) {
            addCriterion("contracts_name not like", value, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameIn(List<String> values) {
            addCriterion("contracts_name in", values, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameNotIn(List<String> values) {
            addCriterion("contracts_name not in", values, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameBetween(String value1, String value2) {
            addCriterion("contracts_name between", value1, value2, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsNameNotBetween(String value1, String value2) {
            addCriterion("contracts_name not between", value1, value2, "contractsName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameIsNull() {
            addCriterion("contracts_coin_name is null");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameIsNotNull() {
            addCriterion("contracts_coin_name is not null");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameEqualTo(String value) {
            addCriterion("contracts_coin_name =", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameNotEqualTo(String value) {
            addCriterion("contracts_coin_name <>", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameGreaterThan(String value) {
            addCriterion("contracts_coin_name >", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameGreaterThanOrEqualTo(String value) {
            addCriterion("contracts_coin_name >=", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameLessThan(String value) {
            addCriterion("contracts_coin_name <", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameLessThanOrEqualTo(String value) {
            addCriterion("contracts_coin_name <=", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameLike(String value) {
            addCriterion("contracts_coin_name like", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameNotLike(String value) {
            addCriterion("contracts_coin_name not like", value, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameIn(List<String> values) {
            addCriterion("contracts_coin_name in", values, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameNotIn(List<String> values) {
            addCriterion("contracts_coin_name not in", values, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameBetween(String value1, String value2) {
            addCriterion("contracts_coin_name between", value1, value2, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andContractsCoinNameNotBetween(String value1, String value2) {
            addCriterion("contracts_coin_name not between", value1, value2, "contractsCoinName");
            return (Criteria) this;
        }

        public Criteria andDepositNumIsNull() {
            addCriterion("deposit_num is null");
            return (Criteria) this;
        }

        public Criteria andDepositNumIsNotNull() {
            addCriterion("deposit_num is not null");
            return (Criteria) this;
        }

        public Criteria andDepositNumEqualTo(BigDecimal value) {
            addCriterion("deposit_num =", value, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumNotEqualTo(BigDecimal value) {
            addCriterion("deposit_num <>", value, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumGreaterThan(BigDecimal value) {
            addCriterion("deposit_num >", value, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("deposit_num >=", value, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumLessThan(BigDecimal value) {
            addCriterion("deposit_num <", value, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("deposit_num <=", value, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumIn(List<BigDecimal> values) {
            addCriterion("deposit_num in", values, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumNotIn(List<BigDecimal> values) {
            addCriterion("deposit_num not in", values, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deposit_num between", value1, value2, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDepositNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deposit_num not between", value1, value2, "depositNum");
            return (Criteria) this;
        }

        public Criteria andDailyOutputIsNull() {
            addCriterion("daily_output is null");
            return (Criteria) this;
        }

        public Criteria andDailyOutputIsNotNull() {
            addCriterion("daily_output is not null");
            return (Criteria) this;
        }

        public Criteria andDailyOutputEqualTo(BigDecimal value) {
            addCriterion("daily_output =", value, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputNotEqualTo(BigDecimal value) {
            addCriterion("daily_output <>", value, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputGreaterThan(BigDecimal value) {
            addCriterion("daily_output >", value, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("daily_output >=", value, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputLessThan(BigDecimal value) {
            addCriterion("daily_output <", value, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputLessThanOrEqualTo(BigDecimal value) {
            addCriterion("daily_output <=", value, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputIn(List<BigDecimal> values) {
            addCriterion("daily_output in", values, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputNotIn(List<BigDecimal> values) {
            addCriterion("daily_output not in", values, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("daily_output between", value1, value2, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andDailyOutputNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("daily_output not between", value1, value2, "dailyOutput");
            return (Criteria) this;
        }

        public Criteria andConsumeIsNull() {
            addCriterion("consume is null");
            return (Criteria) this;
        }

        public Criteria andConsumeIsNotNull() {
            addCriterion("consume is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeEqualTo(BigDecimal value) {
            addCriterion("consume =", value, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeNotEqualTo(BigDecimal value) {
            addCriterion("consume <>", value, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeGreaterThan(BigDecimal value) {
            addCriterion("consume >", value, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("consume >=", value, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeLessThan(BigDecimal value) {
            addCriterion("consume <", value, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("consume <=", value, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeIn(List<BigDecimal> values) {
            addCriterion("consume in", values, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeNotIn(List<BigDecimal> values) {
            addCriterion("consume not in", values, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consume between", value1, value2, "consume");
            return (Criteria) this;
        }

        public Criteria andConsumeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("consume not between", value1, value2, "consume");
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

        public Criteria andRtvTimeIsNull() {
            addCriterion("rtv_time is null");
            return (Criteria) this;
        }

        public Criteria andRtvTimeIsNotNull() {
            addCriterion("rtv_time is not null");
            return (Criteria) this;
        }

        public Criteria andRtvTimeEqualTo(Date value) {
            addCriterion("rtv_time =", value, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeNotEqualTo(Date value) {
            addCriterion("rtv_time <>", value, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeGreaterThan(Date value) {
            addCriterion("rtv_time >", value, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rtv_time >=", value, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeLessThan(Date value) {
            addCriterion("rtv_time <", value, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeLessThanOrEqualTo(Date value) {
            addCriterion("rtv_time <=", value, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeIn(List<Date> values) {
            addCriterion("rtv_time in", values, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeNotIn(List<Date> values) {
            addCriterion("rtv_time not in", values, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeBetween(Date value1, Date value2) {
            addCriterion("rtv_time between", value1, value2, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andRtvTimeNotBetween(Date value1, Date value2) {
            addCriterion("rtv_time not between", value1, value2, "rtvTime");
            return (Criteria) this;
        }

        public Criteria andCycleIsNull() {
            addCriterion("cycle is null");
            return (Criteria) this;
        }

        public Criteria andCycleIsNotNull() {
            addCriterion("cycle is not null");
            return (Criteria) this;
        }

        public Criteria andCycleEqualTo(Integer value) {
            addCriterion("cycle =", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotEqualTo(Integer value) {
            addCriterion("cycle <>", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThan(Integer value) {
            addCriterion("cycle >", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle >=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThan(Integer value) {
            addCriterion("cycle <", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleLessThanOrEqualTo(Integer value) {
            addCriterion("cycle <=", value, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleIn(List<Integer> values) {
            addCriterion("cycle in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotIn(List<Integer> values) {
            addCriterion("cycle not in", values, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleBetween(Integer value1, Integer value2) {
            addCriterion("cycle between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle not between", value1, value2, "cycle");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNull() {
            addCriterion("last_time is null");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNotNull() {
            addCriterion("last_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastTimeEqualTo(Long value) {
            addCriterion("last_time =", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotEqualTo(Long value) {
            addCriterion("last_time <>", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThan(Long value) {
            addCriterion("last_time >", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("last_time >=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThan(Long value) {
            addCriterion("last_time <", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThanOrEqualTo(Long value) {
            addCriterion("last_time <=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeIn(List<Long> values) {
            addCriterion("last_time in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotIn(List<Long> values) {
            addCriterion("last_time not in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeBetween(Long value1, Long value2) {
            addCriterion("last_time between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotBetween(Long value1, Long value2) {
            addCriterion("last_time not between", value1, value2, "lastTime");
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