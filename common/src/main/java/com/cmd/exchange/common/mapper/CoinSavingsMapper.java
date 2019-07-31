package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.CoinSavings;
import com.cmd.exchange.common.model.CoinSavingsRecord;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Mapper
public interface CoinSavingsMapper {
    @Select("select * from t_coin_savings where user_id=#{userId} and coin_name = #{coinName} for update")
    CoinSavings lockCoinSavings(@Param("userId") int userId, @Param("coinName") String coinName);

    @Update("update t_coin_savings set balance=balance + #{changeBalance}, withdrawing=withdrawing + #{changeWithdrawing}"
            + " where user_id=#{userId} and coin_name=#{coinName} and balance>=-#{changeBalance} and withdrawing>=-#{changeWithdrawing}")
    int changeCoinSavings(@Param("userId") int userId, @Param("coinName") String coinName, @Param("changeBalance") BigDecimal changeBalance,
                          @Param("changeWithdrawing") BigDecimal changeWithdrawing);

    @Insert("INSERT INTO t_coin_savings(user_id, coin_name) VALUES (#{userId}, #{coinName})")
    int add(CoinSavings coinSavings);

    @Select("SELECT * FROM t_coin_savings WHERE user_id=#{userId} AND coin_name=#{coinName}")
    CoinSavings getCoinSavingsByUserIdAndCoinName(@Param("userId") Integer userId, @Param("coinName") String coinName);

    @Select("SELECT sum(balance) FROM t_coin_savings WHERE coin_name=#{coinName}")
    BigDecimal getTotalCoinSavings(@Param("coinName") String coinName);

    @Select({"<script>",
            "select cs.*,(select user_name from t_user where id=cs.user_id) as user_name from t_coin_savings cs where 1=1 ",
            "<if test='userId != null'> and user_id=#{userId} </if>",
            "<if test='coinName != null and coinName.length > 0'> and coin_name = #{coinName} </if>",
            "</script>"})
    Page<CoinSavings> getCoinSavings(@Param("userId") Integer userId, @Param("coinName") String coinName, RowBounds rowBounds);

    // 获取资金大于等于指定值的用户存钱信息，因为用户可能比较多，这里只返回部分列内容
    @Select("select user_id,balance from t_coin_savings where coin_name = #{coinName} and balance>=#{minBalance}")
    List<CoinSavings> getUsersByMinBalance(@Param("coinName") String coinName, @Param("minBalance") BigDecimal minBalance);

    /////////////////////////////////////////////////////////////////////////////////////////
    // 下面是存入取出的记录
    @Insert("INSERT INTO t_coin_savings_record(user_id, coin_name,`type`,`amount`,add_time,want_arrival_time,`status`) " +
            "VALUES (#{userId}, #{coinName},#{type},#{amount},NOW(),#{wantArrivalTime},0)")
    int addRecord(CoinSavingsRecord coinSavingsRecord);

    // 获取下一条已经到了期望导致时间的记录
    @Select("select * from t_coin_savings_record where want_arrival_time<=#{wantArrivalTime} and `status`=0 and id>#{lastId} limit 0,1")
    CoinSavingsRecord getNextWantArrivalRecord(@Param("wantArrivalTime") Date wantArrivalTime, @Param("lastId") BigInteger lastId);

    @Select("select * from t_coin_savings_record where id=#{id} for update")
    CoinSavingsRecord lockCoinSavingsRecord(@Param("id") BigInteger id);

    @Update("update t_coin_savings_record set `status`=1,real_arrival_time=NOW() where id=#{id}")
    int setRecordArrival(@Param("id") BigInteger id);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 下面是临时表操作，专门用于bcoin的锁仓挖矿功能
    // 向临时表插入原来不存在的用户记录
    @Insert("insert into tmp_user_saving(user_id,`saving`,`saving_before`) " +
            "  (select user_id,0,`balance` from t_coin_savings where coin_name='BON' and not exists (select user_id from tmp_user_saving where tmp_user_saving.user_id = t_coin_savings.user_id))")
    int addNewUserToTmpTable();

    // 修改临时表的存储币为数据库的新存储量
    @Update("update tmp_user_saving set saving_before = IFNULL((select balance from t_coin_savings where t_coin_savings.user_id=tmp_user_saving.user_id), 0)")
    int renewTmpTableUserCoinBefore();

    // 删除多余的记录
    @Delete("delete from tmp_user_saving where not exists (select user_id from t_coin_savings where t_coin_savings.user_id=tmp_user_saving.user_id)")
    int deleteTmpTableUnUsedRecord();

    // 更新上次数据为最新数据
    @Update("update tmp_user_saving set saving=saving_before")
    int updateTmpTableSaving();

    @Select("select saving from tmp_user_saving where user_id=#{userId}")
    BigDecimal getTmpTableUserSaving(@Param("userId") int userId);
}
