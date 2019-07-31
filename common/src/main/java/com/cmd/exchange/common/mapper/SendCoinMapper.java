package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.SendCoin;
import com.cmd.exchange.common.vo.CoinTransferReqVO;
import com.cmd.exchange.common.vo.CoinTransferVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SendCoinMapper {
    Page<CoinTransferVo> getTransferList(CoinTransferReqVO reqVO, RowBounds rowBounds);

    int add(SendCoin sendCoin);

    /**
     * 第一次审核
     **/
    int updateTransferStatusById(@Param("id") Integer id, @Param("status") Integer status, @Param("txid") String txid);

    /**
     * 第二次审核
     **/
    int updateTransferSecondStatusById(@Param("id") Integer id, @Param("secondStatus") Integer secondStatus, @Param("txid") String txid);

    Page<SendCoin> getTransfer(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("status") Integer status, RowBounds rowBounds);

    //第一次和审核，根据时间查询审核列表
    Page<SendCoin> getTransferByTime(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("status") Integer status,
                                     @Param("startTime") String startTime, @Param("endTime") String endTime,
                                     @Param("groupType") String groupType,
                                     RowBounds rowBounds);

    //第二次和审核，根据时间查询审核列表
    Page<SendCoin> getSecondTransferByTime(@Param("userId") Integer userId, @Param("coinName") String coinName,
                                           @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("groupType") String groupType,
                                           RowBounds rowBounds);

    List<SendCoin> getTransferUnconfirm(@Param("id") Integer id);

    SendCoin getTransferById(@Param("id") Integer id);

    SendCoin lockTransferById(@Param("id") Integer id);

    // 获取指定用户提币的总量
    List<SendCoin> statUserTotalSendSuccessCoins(@Param("userId") Integer userId);

    // 获取所有用户提币的总量
    List<SendCoin> statTotalSendSuccessCoins();

    List<CoinTransferVo> getSendCoinListAllByTime(@Param("coinName") String coinName,
                                                  @Param("lastRefreshTime") Integer lastRefreshTime,
                                                  @Param("rollOutNumber") BigDecimal rollOutNumber);
}
