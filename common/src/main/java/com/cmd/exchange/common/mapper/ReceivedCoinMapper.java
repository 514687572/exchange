package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.vo.CoinTransferReqVO;
import com.cmd.exchange.common.vo.CoinTransferVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ReceivedCoinMapper {
    Page<CoinTransferVo> getTransferList(CoinTransferReqVO reqVO, RowBounds rowBounds);

    List<CoinTransferVo> getReceivedCoinListAllByTimeAndNumber(@Param("lastRefreshTime") Integer lastRefreshTime,
                                                               @Param("coinName") String coinName,
                                                               @Param("rollInNumber") BigDecimal rollInNumber);

    BigDecimal getgetReceivedTotalFee(@Param("lastRefreshTime") Integer lastRefreshTime,
                                      @Param("coinName") String coinName);
}
