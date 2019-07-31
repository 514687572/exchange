package com.cmd.exchange.common.model;

import java.util.ArrayList;
import java.util.List;

public class UserRecommendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserRecommendExample() {
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

        public Criteria andPid1IsNull() {
            addCriterion("pid1 is null");
            return (Criteria) this;
        }

        public Criteria andPid1IsNotNull() {
            addCriterion("pid1 is not null");
            return (Criteria) this;
        }

        public Criteria andPid1EqualTo(Integer value) {
            addCriterion("pid1 =", value, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1NotEqualTo(Integer value) {
            addCriterion("pid1 <>", value, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1GreaterThan(Integer value) {
            addCriterion("pid1 >", value, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid1 >=", value, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1LessThan(Integer value) {
            addCriterion("pid1 <", value, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1LessThanOrEqualTo(Integer value) {
            addCriterion("pid1 <=", value, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1In(List<Integer> values) {
            addCriterion("pid1 in", values, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1NotIn(List<Integer> values) {
            addCriterion("pid1 not in", values, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1Between(Integer value1, Integer value2) {
            addCriterion("pid1 between", value1, value2, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid1NotBetween(Integer value1, Integer value2) {
            addCriterion("pid1 not between", value1, value2, "pid1");
            return (Criteria) this;
        }

        public Criteria andPid2IsNull() {
            addCriterion("pid2 is null");
            return (Criteria) this;
        }

        public Criteria andPid2IsNotNull() {
            addCriterion("pid2 is not null");
            return (Criteria) this;
        }

        public Criteria andPid2EqualTo(Integer value) {
            addCriterion("pid2 =", value, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2NotEqualTo(Integer value) {
            addCriterion("pid2 <>", value, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2GreaterThan(Integer value) {
            addCriterion("pid2 >", value, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid2 >=", value, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2LessThan(Integer value) {
            addCriterion("pid2 <", value, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2LessThanOrEqualTo(Integer value) {
            addCriterion("pid2 <=", value, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2In(List<Integer> values) {
            addCriterion("pid2 in", values, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2NotIn(List<Integer> values) {
            addCriterion("pid2 not in", values, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2Between(Integer value1, Integer value2) {
            addCriterion("pid2 between", value1, value2, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid2NotBetween(Integer value1, Integer value2) {
            addCriterion("pid2 not between", value1, value2, "pid2");
            return (Criteria) this;
        }

        public Criteria andPid3IsNull() {
            addCriterion("pid3 is null");
            return (Criteria) this;
        }

        public Criteria andPid3IsNotNull() {
            addCriterion("pid3 is not null");
            return (Criteria) this;
        }

        public Criteria andPid3EqualTo(Integer value) {
            addCriterion("pid3 =", value, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3NotEqualTo(Integer value) {
            addCriterion("pid3 <>", value, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3GreaterThan(Integer value) {
            addCriterion("pid3 >", value, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid3 >=", value, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3LessThan(Integer value) {
            addCriterion("pid3 <", value, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3LessThanOrEqualTo(Integer value) {
            addCriterion("pid3 <=", value, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3In(List<Integer> values) {
            addCriterion("pid3 in", values, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3NotIn(List<Integer> values) {
            addCriterion("pid3 not in", values, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3Between(Integer value1, Integer value2) {
            addCriterion("pid3 between", value1, value2, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid3NotBetween(Integer value1, Integer value2) {
            addCriterion("pid3 not between", value1, value2, "pid3");
            return (Criteria) this;
        }

        public Criteria andPid4IsNull() {
            addCriterion("pid4 is null");
            return (Criteria) this;
        }

        public Criteria andPid4IsNotNull() {
            addCriterion("pid4 is not null");
            return (Criteria) this;
        }

        public Criteria andPid4EqualTo(Integer value) {
            addCriterion("pid4 =", value, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4NotEqualTo(Integer value) {
            addCriterion("pid4 <>", value, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4GreaterThan(Integer value) {
            addCriterion("pid4 >", value, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid4 >=", value, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4LessThan(Integer value) {
            addCriterion("pid4 <", value, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4LessThanOrEqualTo(Integer value) {
            addCriterion("pid4 <=", value, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4In(List<Integer> values) {
            addCriterion("pid4 in", values, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4NotIn(List<Integer> values) {
            addCriterion("pid4 not in", values, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4Between(Integer value1, Integer value2) {
            addCriterion("pid4 between", value1, value2, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid4NotBetween(Integer value1, Integer value2) {
            addCriterion("pid4 not between", value1, value2, "pid4");
            return (Criteria) this;
        }

        public Criteria andPid5IsNull() {
            addCriterion("pid5 is null");
            return (Criteria) this;
        }

        public Criteria andPid5IsNotNull() {
            addCriterion("pid5 is not null");
            return (Criteria) this;
        }

        public Criteria andPid5EqualTo(Integer value) {
            addCriterion("pid5 =", value, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5NotEqualTo(Integer value) {
            addCriterion("pid5 <>", value, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5GreaterThan(Integer value) {
            addCriterion("pid5 >", value, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid5 >=", value, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5LessThan(Integer value) {
            addCriterion("pid5 <", value, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5LessThanOrEqualTo(Integer value) {
            addCriterion("pid5 <=", value, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5In(List<Integer> values) {
            addCriterion("pid5 in", values, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5NotIn(List<Integer> values) {
            addCriterion("pid5 not in", values, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5Between(Integer value1, Integer value2) {
            addCriterion("pid5 between", value1, value2, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid5NotBetween(Integer value1, Integer value2) {
            addCriterion("pid5 not between", value1, value2, "pid5");
            return (Criteria) this;
        }

        public Criteria andPid6IsNull() {
            addCriterion("pid6 is null");
            return (Criteria) this;
        }

        public Criteria andPid6IsNotNull() {
            addCriterion("pid6 is not null");
            return (Criteria) this;
        }

        public Criteria andPid6EqualTo(Integer value) {
            addCriterion("pid6 =", value, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6NotEqualTo(Integer value) {
            addCriterion("pid6 <>", value, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6GreaterThan(Integer value) {
            addCriterion("pid6 >", value, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid6 >=", value, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6LessThan(Integer value) {
            addCriterion("pid6 <", value, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6LessThanOrEqualTo(Integer value) {
            addCriterion("pid6 <=", value, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6In(List<Integer> values) {
            addCriterion("pid6 in", values, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6NotIn(List<Integer> values) {
            addCriterion("pid6 not in", values, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6Between(Integer value1, Integer value2) {
            addCriterion("pid6 between", value1, value2, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid6NotBetween(Integer value1, Integer value2) {
            addCriterion("pid6 not between", value1, value2, "pid6");
            return (Criteria) this;
        }

        public Criteria andPid7IsNull() {
            addCriterion("pid7 is null");
            return (Criteria) this;
        }

        public Criteria andPid7IsNotNull() {
            addCriterion("pid7 is not null");
            return (Criteria) this;
        }

        public Criteria andPid7EqualTo(Integer value) {
            addCriterion("pid7 =", value, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7NotEqualTo(Integer value) {
            addCriterion("pid7 <>", value, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7GreaterThan(Integer value) {
            addCriterion("pid7 >", value, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid7 >=", value, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7LessThan(Integer value) {
            addCriterion("pid7 <", value, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7LessThanOrEqualTo(Integer value) {
            addCriterion("pid7 <=", value, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7In(List<Integer> values) {
            addCriterion("pid7 in", values, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7NotIn(List<Integer> values) {
            addCriterion("pid7 not in", values, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7Between(Integer value1, Integer value2) {
            addCriterion("pid7 between", value1, value2, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid7NotBetween(Integer value1, Integer value2) {
            addCriterion("pid7 not between", value1, value2, "pid7");
            return (Criteria) this;
        }

        public Criteria andPid8IsNull() {
            addCriterion("pid8 is null");
            return (Criteria) this;
        }

        public Criteria andPid8IsNotNull() {
            addCriterion("pid8 is not null");
            return (Criteria) this;
        }

        public Criteria andPid8EqualTo(Integer value) {
            addCriterion("pid8 =", value, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8NotEqualTo(Integer value) {
            addCriterion("pid8 <>", value, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8GreaterThan(Integer value) {
            addCriterion("pid8 >", value, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid8 >=", value, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8LessThan(Integer value) {
            addCriterion("pid8 <", value, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8LessThanOrEqualTo(Integer value) {
            addCriterion("pid8 <=", value, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8In(List<Integer> values) {
            addCriterion("pid8 in", values, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8NotIn(List<Integer> values) {
            addCriterion("pid8 not in", values, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8Between(Integer value1, Integer value2) {
            addCriterion("pid8 between", value1, value2, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid8NotBetween(Integer value1, Integer value2) {
            addCriterion("pid8 not between", value1, value2, "pid8");
            return (Criteria) this;
        }

        public Criteria andPid9IsNull() {
            addCriterion("pid9 is null");
            return (Criteria) this;
        }

        public Criteria andPid9IsNotNull() {
            addCriterion("pid9 is not null");
            return (Criteria) this;
        }

        public Criteria andPid9EqualTo(Integer value) {
            addCriterion("pid9 =", value, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9NotEqualTo(Integer value) {
            addCriterion("pid9 <>", value, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9GreaterThan(Integer value) {
            addCriterion("pid9 >", value, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid9 >=", value, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9LessThan(Integer value) {
            addCriterion("pid9 <", value, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9LessThanOrEqualTo(Integer value) {
            addCriterion("pid9 <=", value, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9In(List<Integer> values) {
            addCriterion("pid9 in", values, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9NotIn(List<Integer> values) {
            addCriterion("pid9 not in", values, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9Between(Integer value1, Integer value2) {
            addCriterion("pid9 between", value1, value2, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid9NotBetween(Integer value1, Integer value2) {
            addCriterion("pid9 not between", value1, value2, "pid9");
            return (Criteria) this;
        }

        public Criteria andPid10IsNull() {
            addCriterion("pid10 is null");
            return (Criteria) this;
        }

        public Criteria andPid10IsNotNull() {
            addCriterion("pid10 is not null");
            return (Criteria) this;
        }

        public Criteria andPid10EqualTo(Integer value) {
            addCriterion("pid10 =", value, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10NotEqualTo(Integer value) {
            addCriterion("pid10 <>", value, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10GreaterThan(Integer value) {
            addCriterion("pid10 >", value, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid10 >=", value, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10LessThan(Integer value) {
            addCriterion("pid10 <", value, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10LessThanOrEqualTo(Integer value) {
            addCriterion("pid10 <=", value, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10In(List<Integer> values) {
            addCriterion("pid10 in", values, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10NotIn(List<Integer> values) {
            addCriterion("pid10 not in", values, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10Between(Integer value1, Integer value2) {
            addCriterion("pid10 between", value1, value2, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid10NotBetween(Integer value1, Integer value2) {
            addCriterion("pid10 not between", value1, value2, "pid10");
            return (Criteria) this;
        }

        public Criteria andPid11IsNull() {
            addCriterion("pid11 is null");
            return (Criteria) this;
        }

        public Criteria andPid11IsNotNull() {
            addCriterion("pid11 is not null");
            return (Criteria) this;
        }

        public Criteria andPid11EqualTo(Integer value) {
            addCriterion("pid11 =", value, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11NotEqualTo(Integer value) {
            addCriterion("pid11 <>", value, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11GreaterThan(Integer value) {
            addCriterion("pid11 >", value, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11GreaterThanOrEqualTo(Integer value) {
            addCriterion("pid11 >=", value, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11LessThan(Integer value) {
            addCriterion("pid11 <", value, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11LessThanOrEqualTo(Integer value) {
            addCriterion("pid11 <=", value, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11In(List<Integer> values) {
            addCriterion("pid11 in", values, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11NotIn(List<Integer> values) {
            addCriterion("pid11 not in", values, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11Between(Integer value1, Integer value2) {
            addCriterion("pid11 between", value1, value2, "pid11");
            return (Criteria) this;
        }

        public Criteria andPid11NotBetween(Integer value1, Integer value2) {
            addCriterion("pid11 not between", value1, value2, "pid11");
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