package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.TradeWarningDetailMapper;
import com.cmd.exchange.common.model.TradeWarningDetail;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeWarningDetailService {
    private static Logger logger = LoggerFactory.getLogger(TradeWarningDetailService.class);

    @Autowired
    private TradeWarningDetailMapper tradeWarningDetailMapper;


    //获取所有的大单详情列表
    public List<TradeWarningDetail> getTradeWarningDetailList() {
        return tradeWarningDetailMapper.getTradeWarningDetailList();
    }

    //获取某个警告类型的所有大单详情
    public List<TradeWarningDetail> getTrdeWarningDetailByWarningType(String warningType) {
        return tradeWarningDetailMapper.getTrdeWarningDetailByWarningType(warningType);
    }

    //根据主键获取某个订单信息
    public TradeWarningDetail getTradeWarningDetailById(Integer id) {
        return tradeWarningDetailMapper.getTradeWarningDetailById(id);
    }

    public List<TradeWarningDetail> getTradeWarningDetailBySureType(Integer sureType) {
        return tradeWarningDetailMapper.getTradeWarningDetailBySureType(sureType);
    }

    public int addTradeWarningDetail(TradeWarningDetail tradeWarningDetail) {
        return tradeWarningDetailMapper.addTradeWarningDetail(tradeWarningDetail);
    }

    public int updateTradeWarningDetail(TradeWarningDetail tradeWarningDetail) {
        return tradeWarningDetailMapper.updateTradeWarningDetail(tradeWarningDetail);
    }

    public void deleteTradeWarningDetailById(Integer id) {
        tradeWarningDetailMapper.deleteTradeWarningDetailById(id);
    }


}
