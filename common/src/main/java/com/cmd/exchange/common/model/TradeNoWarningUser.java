package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TradeNoWarningUser implements Serializable {
    private static final long serialVersionUID = -1L;
    @ApiModelProperty("'主键'")
    private Integer id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("创建时间")
    private Date createTime = new Date();
    @ApiModelProperty("警告类型:挂单警告，高频交易，")
    private String noWarningType;
    @ApiModelProperty("'用户id'")
    private Integer userId;

}
