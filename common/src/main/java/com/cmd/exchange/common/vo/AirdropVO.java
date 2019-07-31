package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.Airdrop;
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
public class AirdropVO implements Serializable {
    private static final long serialVersionUID = -4247999277428744816L;
    @ApiModelProperty(value = "ID主键")
    private Integer id;
    @JsonIgnore
    private Integer userId;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @ApiModelProperty(value = "收益币种，收益配置表币种的类型ID", required = true)
    private Integer type;
    @ApiModelProperty(value = "状态 1:未开始 2:已关闭 3：已完成", required = true)
    private Integer status;
    @ApiModelProperty(value = "积分要求下限", required = true)
    private BigDecimal integrationMin;
    @ApiModelProperty(value = "积分要求上限", required = true)
    private BigDecimal integrationMax;
    @ApiModelProperty(value = "空投数量（每人可获得的数量）", required = true)
    private BigDecimal airdropNum;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private Integer userNum;

    public Airdrop toModel() {
        Airdrop model = new Airdrop();
        BeanUtils.copyProperties(this, model);
        return model;
    }
}
