package com.cmd.exchange.common.mapper;


import com.cmd.exchange.common.model.MiningStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * 挖矿奖励统计，每天统计一次
 */
@Mapper
public interface MiningStatMapper {
    @Select("select * from t_mining_stat where id=#{id}")
    MiningStat getStatById(int id);

    @Select("select * from t_mining_stat where stat_time=#{time}")
    MiningStat getStatByTime(int time);

    @Update("update t_mining_stat set trade_bonus=#{tradeBonus},trade_rec_bonus=#{tradeRecBonus},other_bonus=#{otherBonus}"
            + " where stat_time=#{time}")
    int updateMiningStatByTime(@Param("time") int time, @Param("tradeBonus") BigDecimal tradeBonus,
                               @Param("tradeRecBonus") BigDecimal tradeRecBonus, @Param("otherBonus") BigDecimal otherBonus);

    @Update("insert into t_mining_stat(stat_time,trade_bonus,trade_rec_bonus,other_bonus) "
            + "values(#{time}, #{tradeBonus}, #{tradeRecBonus},#{otherBonus})")
    int add(@Param("time") int time, @Param("tradeBonus") BigDecimal tradeBonus, @Param("tradeRecBonus") BigDecimal tradeRecBonus,
            @Param("otherBonus") BigDecimal otherBonus);

    /**
     * 获取最后一条统计记录
     *
     * @return 最后一条记录或null
     */
    @Select("select * from t_mining_stat order by stat_time desc limit 0,1")
    MiningStat getLastStat();

    @Select("select sum(trade_bonus + trade_rec_bonus + other_bonus) from t_mining_stat")
    BigDecimal getSumMiningAndBonus();
}
