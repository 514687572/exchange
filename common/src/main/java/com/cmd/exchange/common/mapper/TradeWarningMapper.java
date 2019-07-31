package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.TradeWarning;

import com.cmd.exchange.common.vo.TradeWarningVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface TradeWarningMapper {

    //根据警告类型获取配置信息
    List<TradeWarning> getTradeWarningListAll();

    //获取所有的配置信息
    Page<TradeWarningVO> getTradeWarningList(@Param("marketId") Integer marketId, RowBounds rowBounds);

    TradeWarningVO getTradeWarningById(@Param("id") Integer id);

    int addTradeWarning(TradeWarning tradeWarning);

    int updateTradeWarningById(TradeWarning tradeWarning);

    void deleteTradeWarningById(@Param("id") Integer id);

    TradeWarning getTradeWarningByMarketId(@Param("marketId") Integer marketId, @Param("type") Integer type);


}
