package com.cmd.exchange.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("用户的交易返佣信息")
public class TradeReturnRewardVo {
    public static class RewardInfo {
        public String coinName;
        public BigDecimal reward;
    }

    @ApiModelProperty("推荐的一级和二级用户总数")
    private int recUserCount;
    @ApiModelProperty("用户推荐返佣总和")
    private List<RewardInfo> rewards = new ArrayList<>();

}
