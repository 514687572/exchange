package com.cmd.exchange.common.vo;

import com.cmd.exchange.common.model.CoinGroupConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ConfigGroupConfigVO {

    @ApiModelProperty("分组id")
    private int groupId;
    @ApiModelProperty("分组类型标记")
    private String groupType;
    @ApiModelProperty("分组名")
    private String groupName;
    @ApiModelProperty("改分组下的币种列表")
    private List<CoinGroupConfig> coinGroupConfigList;
}
