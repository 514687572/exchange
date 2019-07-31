package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserIntegration {
    private Integer userId;

    private BigDecimal userIntegration;

    private Integer integrationType;

    private Date addTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getUserIntegration() {
        return userIntegration;
    }

    public void setUserIntegration(BigDecimal userIntegration) {
        this.userIntegration = userIntegration;
    }

    public Integer getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(Integer integrationType) {
        this.integrationType = integrationType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}