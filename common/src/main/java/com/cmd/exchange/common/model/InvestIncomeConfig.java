package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class InvestIncomeConfig {
    private Integer id;

    private Integer creatorId;

    private Date createTime;

    private Date updateTime;

    private Integer type;

    private BigDecimal staticIncom;

    private BigDecimal dynamicIncom;

    private BigDecimal dynamicScale;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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

    public BigDecimal getStaticIncom() {
        return staticIncom;
    }

    public void setStaticIncom(BigDecimal staticIncom) {
        this.staticIncom = staticIncom;
    }

    public BigDecimal getDynamicIncom() {
        return dynamicIncom;
    }

    public void setDynamicIncom(BigDecimal dynamicIncom) {
        this.dynamicIncom = dynamicIncom;
    }

    public BigDecimal getDynamicScale() {
        return dynamicScale;
    }

    public void setDynamicScale(BigDecimal dynamicScale) {
        this.dynamicScale = dynamicScale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}