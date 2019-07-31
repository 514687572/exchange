package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DelayRewardTradeFee;
import com.cmd.exchange.common.model.UserAmountTransferredIntoLog;
import com.cmd.exchange.common.vo.RewardLogVO;
import com.cmd.exchange.common.vo.TradeBonusLogRequestVO;
import com.cmd.exchange.common.vo.TradeBonusLogVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 外部用户金额转入内部对应用户日志
 */
@Mapper
public interface UserAmountTransferredIntoLogMapper {

    int add(UserAmountTransferredIntoLog userAmountTransferredIntoLog);
}
