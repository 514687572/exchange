package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.TradeWarningDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TradeWarningDetailMapper {

    //获取所有的大单详情列表
    List<TradeWarningDetail> getTradeWarningDetailList();

    //获取某个警告类型的所有大单详情
    List<TradeWarningDetail> getTrdeWarningDetailByWarningType(@Param("warningType") String warningType);

    //根据主键获取某个订单信息
    TradeWarningDetail getTradeWarningDetailById(@Param("id") Integer id);

    List<TradeWarningDetail> getTradeWarningDetailBySureType(@Param("sureType") Integer sureType);

    int addTradeWarningDetail(TradeWarningDetail tradeWarningDetail);

    int updateTradeWarningDetail(TradeWarningDetail tradeWarningDetail);

    void deleteTradeWarningDetailById(@Param("id") Integer id);

}
