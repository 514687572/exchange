package com.cmd.exchange.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "编辑币种分组配置接受数据对象")
@Getter
@Setter
@NoArgsConstructor
public class EditCoinGroupConfigSVO {

    @ApiModelProperty(value = "具体需要修改的列表")
    @NotNull
    private List<EditCoinGroupConfigDetailVO> list;

}
