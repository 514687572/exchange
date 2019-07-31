package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class SmartContracts {
    private Integer id;

    private String contractsName;

    private String contractsCoinName;

    private BigDecimal depositNum;

    private BigDecimal dailyOutput;

    private Integer cycle;

    private BigDecimal consume;

    private Integer state;

    private Integer contractsLimit;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getConsume() {
        return consume;
    }

    public void setConsume(BigDecimal consume) {
        this.consume = consume;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getContractsLimit() {
        return contractsLimit;
    }

    public void setContractsLimit(Integer contractsLimit) {
        this.contractsLimit = contractsLimit;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}