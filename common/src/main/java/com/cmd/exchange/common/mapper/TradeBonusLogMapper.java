package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DelayRewardTradeFee;
import com.cmd.exchange.common.vo.RewardLogVO;
import com.cmd.exchange.common.vo.TradeBonusLogRequestVO;
import com.cmd.exchange.common.vo.TradeBonusLogVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 交易奖励日志（挖矿返佣日志）
 */
@Mapper
public interface TradeBonusLogMapper {

    @Insert("insert into t_trade_bonus_log(user_id,coin_name,settlement_currency,trade_log_id,`type`,trade_fee,recommend_user_id,trade_bonus,recommend_bonus,create_time)"
            + "values(#{userId},#{coinName},#{settlementCurrency},#{tradeLogId},#{type},#{tradeFee},#{recommendUserId},#{tradeBonus},#{recommendBonus},NOW())")
    int addLog(@Param("userId") int userId, @Param("coinName") String coinName, @Param("settlementCurrency") String settlementCurrency,
               @Param("tradeLogId") BigInteger tradeLogId, @Param("type") int type, @Param("tradeFee") BigDecimal tradeFee,
               @Param("recommendUserId") int recommendUserId, @Param("tradeBonus") BigDecimal tradeBonus, @Param("recommendBonus") BigDecimal recommendBonus);

    Page<TradeBonusLogVO> getTradeBonusList(TradeBonusLogRequestVO request, RowBounds rowBounds);

    Page<RewardLogVO> getTradeBonusLog(@Param("userId") int userId, RowBounds rowBounds);

    @Insert("insert into t_delay_reward_trade(user_id,coin_name,change_amount,comment,add_time)"
            + "values(#{userId},#{coinName},#{changeAmount},#{comment}, NOW())")
    void addDelayRewardTradeFee(@Param("userId") int userId, @Param("coinName") String coinName,
                                @Param("changeAmount") BigDecimal changeAmount, @Param("comment") String comment);

    @Select("select * from t_delay_reward_trade order by id asc limit 0,1")
    DelayRewardTradeFee getFirstDelayRewardTradeFee();

    @Delete("delete from t_delay_reward_trade where id=#{id}")
    int deleteDelayRewardTradeFee(@Param("id") BigInteger id);
}
