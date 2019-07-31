package com.cmd.exchange.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserGroupConfig implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    @ApiModelProperty(value = "分组类型 ：0:表示默认属性 A,B,C,D")
    private String groupType;
    @ApiModelProperty(value = "名字")
    private String groupName;
    @ApiModelProperty(value = "状态 0:表示未禁用 1.表示禁用")
    private Integer status = 0;

}
