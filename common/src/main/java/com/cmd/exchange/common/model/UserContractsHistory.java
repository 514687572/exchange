package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserContractsHistory {
    private Integer id;

    private Integer userId;

    private String contractsName;

    private String contractsCoinName;

    private BigDecimal depositNum;

    private BigDecimal dailyOutput;

    private BigDecimal consume;

    private Date addTime;

    private Date rtvTime;

    private Integer cycle;

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

    public String getContractsName() {
        return contractsName;
    }

    public void setContractsName(String contractsName) {
        this.contractsName = contractsName == null ? null : contractsName.trim();
    }

    public String getContractsCoinName() {
        return contractsCoinName;
    }

    public void setContractsCoinName(String contractsCoinName) {
        this.contractsCoinName = contractsCoinName == null ? null : contractsCoinName.trim();
    }

    public BigDecimal getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(BigDecimal depositNum) {
        this.depositNum = depositNum;
    }

    public BigDecimal getDailyOutput() {
        return dailyOutput;
    }

    public void setDailyOutput(BigDecimal dailyOutput) {
        this.dailyOutput = dailyOutput;
    }

    public BigDecimal getConsume() {
        return consume;
    }

    public void setConsume(BigDecimal consume) {
        this.consume = consume;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getRtvTime() {
        return rtvTime;
    }

    public void setRtvTime(Date rtvTime) {
        this.rtvTime = rtvTime;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }
}