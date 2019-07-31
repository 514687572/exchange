package com.cmd.exchange.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoinGroupConfigVO implements Serializable {

    private static final long serialVersionUID = -1L;
    @ApiModelProperty("分组Id")
    private Integer groupId;
    @ApiModelProperty("分组类型标记")
    private String groupType;
    @ApiModelProperty("分组名")
    private String groupName;

    @ApiModelProperty("对应的分组")
    private Long id;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("费率")
    private String conValue;

    // @ApiModelProperty("每个分组下面的列表详情")
    // private List<CoinGroupConfigDetailVO> coinGroupConfigDetailVOList;
}
