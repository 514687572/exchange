package com.cmd.exchange.common.model;

public class UserTeam {
    private Integer userId;

    private Integer childrenUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChildrenUserId() {
        return childrenUserId;
    }

    public void setChildrenUserId(Integer childrenUserId) {
        this.childrenUserId = childrenUserId;
    }
}