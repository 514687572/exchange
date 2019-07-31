package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserCoin;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 用于做分红的数据库操作
 */
@Mapper
public interface ShareOutBonusMapper {
    public static class UserBalance {
        public int userId;
        public BigDecimal availableBalance;
    }

    /**
     * 删除临时表的数据
     *
     * @return
     */
    @Delete("delete from tmp_user_coin")
    int deleteTmpUserCoins();

    /**
     * 产生对应币种的每个用户的可用资金
     *
     * @param coinName
     * @return
     */
    @Insert("insert into tmp_user_coin(user_id,available_balance) select user_id,available_balance from t_user_coin where available_balance>0 and coin_name=#{coinName}")
    int genTmpUserCoins(String coinName);

    @Select("select sum(available_balance) from tmp_user_coin")
    BigDecimal getTotalBalance();

    /**
     * 获取下一个用户的资金
     *
     * @param lastUserId 上一个用户id
     * @return
     */
    @Select("select * from tmp_user_coin where user_id > #{lastUserId} and available_balance >= #{minBalance} order by user_id asc limit 0,1")
    UserBalance getNextUserBalance(@Param("lastUserId") int lastUserId, @Param("minBalance") BigDecimal minBalance);

    @Select("select * from tmp_user_coin where available_balance >= #{minBalance}")
    List<UserBalance> getUserBalances(@Param("minBalance") BigDecimal minBalance);
}
