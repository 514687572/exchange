package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserCoin;
import com.cmd.exchange.common.vo.UserCoinInfoVO;
import com.cmd.exchange.common.vo.UserCoinVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserCoinMapper {
    @Select("select * from t_user_coin where user_id=#{userId} and coin_name = #{coinName} for update")
    UserCoin lockUserCoin(@Param("userId") int userId, @Param("coinName") String coinName);

    @Update("update t_user_coin set available_balance=available_balance + #{changeAvailableBalance}, freeze_balance=freeze_balance + #{changeFreezeBalance}"
            + " where user_id=#{userId} and coin_name=#{coinName} and available_balance>=-#{changeAvailableBalance} and freeze_balance>=-#{changeFreezeBalance}")
    int changeUserCoin(@Param("userId") int userId, @Param("coinName") String coinName, @Param("changeAvailableBalance") BigDecimal changeAvailableBalance,
                       @Param("changeFreezeBalance") BigDecimal changeFreezeBalance);

    @Update("update t_user_coin set available_balance=available_balance + #{changeAvailableBalance}, receive_freeze_balance=receive_freeze_balance + #{changeReceiveFreezeBalance}"
            + " where user_id=#{userId} and coin_name=#{coinName} and available_balance>=-#{changeAvailableBalance} and receive_freeze_balance>=-#{changeReceiveFreezeBalance}")
    int changeUserLockedWarehouseCoin(@Param("userId") int userId, @Param("coinName") String coinName, @Param("changeAvailableBalance") BigDecimal changeAvailableBalance,
                                      @Param("changeReceiveFreezeBalance") BigDecimal changeReceiveFreezeBalance);

    @Update("update t_user_coin set receive_freeze_balance=receive_freeze_balance + #{changeFreezeBalance}"
            + " where user_id=#{userId} and coin_name=#{coinName} and receive_freeze_balance>=-#{changeFreezeBalance}")
    int changeUserReceivedFreezeCoin(@Param("userId") int userId, @Param("coinName") String coinName, @Param("changeFreezeBalance") BigDecimal changeFreezeBalance);

    @Update("update t_user_coin set available_balance=available_balance + #{changeFreezeBalance}, receive_freeze_balance=receive_freeze_balance - #{changeFreezeBalance}"
            + " where user_id=#{userId} and coin_name=#{coinName} and receive_freeze_balance>=-#{changeFreezeBalance}")
    int receivedFreezeCoinToAvailable(@Param("userId") int userId, @Param("coinName") String coinName, @Param("changeFreezeBalance") BigDecimal changeFreezeBalance);

    /**
     * @param userId
     * @param coinName
     * @param deductionIntegral
     * @return
     */
    int changeUserDeductionIntegral(@Param("userId") int userId, @Param("coinName") String coinName, @Param("deductionIntegral") BigDecimal deductionIntegral);


    @Select("select user_id from t_user_coin where coin_name=#{coinName} and bind_address=#{address}")
    Integer getUserIdByCoinNameAndAddress(@Param("coinName") String coinName, @Param("address") String address);


    int add(UserCoin userCoin);

    int updateUserCoinAddress(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("bindAddress") String bindAddress);

    UserCoinVO getUserCoinByUserIdAndCoinName(@Param("userId") Integer userId, @Param("coinName") String coinName);

    UserCoinVO lockUserCoinByUserIdAndCoinName(@Param("userId") Integer userId, @Param("coinName") String coinName);

    UserCoin getUserCoinByAddressAndCoinName(@Param("address") String address, @Param("coinName") String coinName);

    List<UserCoinVO> getUserCoinByUserId(@Param("userId") Integer userId);

    List<UserCoinVO> getCoinConfigList(@Param("coinNameList") List<String> coinName);

    @Select("select sum(available_balance+freeze_balance) from t_user_coin where coin_name=#{coinName}")
    BigDecimal getSumOfCoin(@Param("coinName") String coinName);

    @Select("select sum(available_balance) from t_user_coin where coin_name=#{coinName}")
    BigDecimal getSumOfAvailableCoin(@Param("coinName") String coinName);

    Page<UserCoinInfoVO> getUserCoinInfo(@Param("userId") Integer userId, @Param("coinName") String coinName,
                                         @Param("groupType") String groupType, @Param("realName") String realName,
                                         @Param("column") String column, @Param("desc") boolean desc, RowBounds rowBounds);

    @Select("select bind_address from t_user_coin where coin_name=#{coinName} and bind_address is not NULL")
    List<String> getAllAddressByCoinName(@Param("coinName") String coinName);

    BigDecimal getUserCoinAmountByCoinName(@Param("coinName") String coinName, @Param("groupType") String groupType);

    List<UserCoinInfoVO> getUserCoinInfoAll(@Param("userId") Integer userId, @Param("coinName") String coinName,
                                            @Param("groupType") String groupType, @Param("realName") String realName,
                                            @Param("column") String column, @Param("desc") boolean desc);

    @Update("update t_user_coin set vip_type=#{vipType} where user_id=#{userId} and coin_name = #{coinName}")
    int setUserCoinVipType(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("vipType") Integer vipType);
}
