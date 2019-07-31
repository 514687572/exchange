package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.ReceivedCoin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TransferMapper {
    /**
     * 增加一个收到币的记录
     */
    @Insert("insert into t_received_coin(user_id,address,coin_name,txid,amount,fee,tx_time,received_time,status)"
            + " select #{userId},#{address},#{coinName},#{txId},#{amount},#{fee},#{txTime},unix_timestamp(),#{status}"
            + " from dual where not exists (select id from  t_received_coin where coin_name=#{coinName} and txid=#{txId})")
    int addReceivedCoin(@Param("userId") int userId, @Param("address") String address, @Param("coinName") String coinName,
                        @Param("txId") String txId, @Param("amount") BigDecimal amount, @Param("fee") BigDecimal fee,
                        @Param("txTime") int txTime, @Param("status") int status);

    /**
     * 根据币种名称和交易id获取交易信息
     *
     * @param coinName 币种名称
     * @param txId     交易id
     * @return ReceivedCoin
     */
    @Select("select * from t_received_coin where coin_name=#{coinName} and txid=#{txId} limit 0,1")
    ReceivedCoin getReceivedCoinId(@Param("coinName") String coinName, @Param("txId") String txId);

    /**
     * 统计从外部转账进来的币种
     *
     * @return
     */
    @Select("select sum(amount) as amount,coin_name from t_received_coin where left(txid, 6) != 'inner-' group by coin_name")
    List<ReceivedCoin> getTotalReceiveFromExternal();

    /**
     * 统计某个用户从外部转账进来的币种
     *
     * @return
     */
    @Select("select sum(amount) as amount,coin_name from t_received_coin where user_id=#{userId} and left(txid, 6) != 'inner-' group by coin_name")
    List<ReceivedCoin> getUserTotalReceiveFromExternal(@Param("userId") int userId);

    /**
     * 统计从外部转账进来的币种
     *
     * @return
     */
    @Select("select sum(amount) as amount,coin_name from t_received_coin where received_time between #{beginTime} and #{endTime} and left(txid, 6) != 'inner-' group by coin_name")
    List<ReceivedCoin> getTotalReceiveFromExternalWithTime(@Param("beginTime") int beginTime, @Param("endTime") int endTime);

    @Select("select * from t_received_coin where id>#{lastId} and coin_name=#{coinName} and txid!='-1' and left(txid, 6) != 'inner-' order by id asc limit 0,1")
    ReceivedCoin getNextReceiveFromExternal(@Param("lastId") int lastId, @Param("coinName") String coinName);
}
