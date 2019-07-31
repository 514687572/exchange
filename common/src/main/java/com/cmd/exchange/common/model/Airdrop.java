package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class Airdrop {
    private Integer id;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

    private Integer type;

    private Integer status;

    private BigDecimal integrationMin;

    private BigDecimal integrationMax;

    private BigDecimal airdropNum;

    private Date doTime;

    private Integer userNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getIntegrationMin() {
        return integrationMin;
    }

    public void setIntegrationMin(BigDecimal integrationMin) {
        this.integrationMin = integrationMin;
    }

    public BigDecimal getIntegrationMax() {
        return integrationMax;
    }

    public void setIntegrationMax(BigDecimal integrationMax) {
        this.integrationMax = integrationMax;
    }

    public BigDecimal getAirdropNum() {
        return airdropNum;
    }

    public void setAirdropNum(BigDecimal airdropNum) {
        this.airdropNum = airdropNum;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }
}