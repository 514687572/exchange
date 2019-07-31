package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.InvestIncomeConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InvestIncomeConfigVO implements Serializable {
    private static final long serialVersionUID = -4247999277428744815L;
    @ApiModelProperty(value = "ID主键")
    private Integer id;
    @JsonIgnore
    private Integer creatorId;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Integer type;
    @ApiModelProperty(value = "静态收益比例", required = true)
    private BigDecimal staticIncom;
    @ApiModelProperty(value = "动态收益比例", required = true)
    private BigDecimal dynamicIncom;
    @ApiModelProperty(value = "动态入账比例", required = true)
    private BigDecimal dynamicScale;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String description;

    public InvestIncomeConfig toModel() {
        InvestIncomeConfig model = new InvestIncomeConfig();
        BeanUtils.copyProperties(this, model);
        return model;
    }
}
