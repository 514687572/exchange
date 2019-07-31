package com.cmd.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

public class InvestDetail {
    private Integer id;

    private Integer investUserId;

    private BigDecimal amount;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvestUserId() {
        return investUserId;
    }

    public void setInvestUserId(Integer investUserId) {
        this.investUserId = investUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}