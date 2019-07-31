package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserDayProfit;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface UserDayProfitMapper {
    // 初始化用户的收益数据
    @Insert("replace into t_user_day_profit(user_id,profit_time,referrer) select id, #{date},referrer from t_user")
    int initUserProfitOfDay(@Param("date") Date date);

    // 获取用户最后一条收益数据
    @Select("select * from t_user_day_profit where user_id=#{userId} order by profit_time desc limit 0,1")
    UserDayProfit getUserLastProfit(@Param("userId") int userId);

    // 更新收益数据
    @Update({"<script>",
            "update t_user_day_profit set id=id",
            "<if test='usdtUseAmount != null'>,usdt_use_amount=#{usdtUseAmount} </if>",
            "<if test='usdtGetAmount != null'>,usdt_get_amount=#{usdtGetAmount} </if>",
            "<if test='usdtTotalFee != null'>,usdt_total_fee=#{usdtTotalFee} </if>",
            " where user_id=#{userId} and profit_time=#{profitTime}",
            "</script>"})
    int updateUserTradeStat(@Param("userId") int userId, @Param("profitTime") Date profitTime, @Param("usdtUseAmount") BigDecimal usdtUseAmount,
                            @Param("usdtGetAmount") BigDecimal usdtGetAmount, @Param("usdtTotalFee") BigDecimal usdtTotalFee);
    //超级节点 昨日收益
    @Update("update t_user_day_profit set super_node_profit=IFNULL((select change_amount from t_user_bill where user_id=t_user_day_profit.user_id " +
            " and `reason`=#{reason} and last_time >= #{profitTime} and last_time < date_add(#{profitTime}, interval 1 day) order by id desc limit 0,1),0)" +
            " where profit_time=#{profitTime}")
    int updateSuperNodeShareOut(@Param("profitTime") Date profitTime, @Param("reason") String reason);
    //超级节点 总收益
    @Update("update t_user_day_profit set super_node_total_profit=IFNULL((select sum(change_amount) from t_user_bill where user_id=t_user_day_profit.user_id " +
            " and `reason`=#{reason}),0)" +
            " where profit_time=#{profitTime}")
    int updateSuperNodeShareOutTotal(@Param("profitTime") Date profitTime, @Param("reason") String reason);

    @Update("update t_user_day_profit set saving_profit=IFNULL((select change_amount from t_user_bill where user_id=t_user_day_profit.user_id " +
            " and `reason`=#{reason} and last_time >= #{profitTime} and last_time < date_add(#{profitTime}, interval 1 day) order by id desc limit 0,1),0)" +
            " where profit_time=#{profitTime}")
    int updateCoinSavingShareOut(@Param("profitTime") Date profitTime, @Param("reason") String reason);

    @Update("update t_user_day_profit set trade_reward_uks=IFNULL((select sum(change_amount) from t_user_bill where user_id=t_user_day_profit.user_id " +
            " and `reason`=#{reason} and last_time < #{profitTime} and last_time >= date_sub(#{profitTime}, interval 1 day) ),0)" +
            " where profit_time=#{profitTime}")
    int updateTradeRewardUks(@Param("profitTime") Date profitTime, @Param("reason") String reason);
    //团队收益  昨日团队奖励
    @Update("update t_user_day_profit set usdt_team_reward=IFNULL((select sum(change_amount) from t_user_bill where user_id=t_user_day_profit.user_id " +
            " and `reason`=#{reason} and last_time < #{profitTime} and last_time >= date_sub(#{profitTime}, interval 1 day) ),0)" +
            " where profit_time=#{profitTime}")
    int updateTradeRewardTeam(@Param("profitTime") Date profitTime, @Param("reason") String reason);
    //团队收益  总收益
    @Update("update t_user_day_profit set usdt_team_total_reward=IFNULL((select sum(change_amount) from t_user_bill where user_id=t_user_day_profit.user_id " +
            " and `reason`=#{reason}),0)" +
            " where profit_time=#{profitTime}")
    int updateTradeRewardTeamTotal(@Param("profitTime") Date profitTime, @Param("reason") String reason);

    @Select("call CalTeamUsdt(#{profitTime})")
    void statTeamTrade(@Param("profitTime") Date profitTime);
}
