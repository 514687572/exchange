package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.TradeWarningMapper;
import com.cmd.exchange.common.model.TradeWarning;
import com.cmd.exchange.common.vo.TradeWarningVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeWarningService {

    private static Logger logger = LoggerFactory.getLogger(TradeWarningService.class);

    @Autowired
    private TradeWarningMapper tradeWarningMapper;

    //根据警告类型获取配置信息
    public List<TradeWarning> getTradeWarningListByType(String warningType) {
        return tradeWarningMapper.getTradeWarningListAll();
    }

    //获取所有的配置信息
    public Page<TradeWarningVO> getTradeWarningList(Integer marketId, Integer pageNo, Integer pageSize) {

        return tradeWarningMapper.getTradeWarningList(marketId, new RowBounds(pageNo, pageSize));
    }

    public TradeWarningVO getTradeWarningById(Integer id) {
        return tradeWarningMapper.getTradeWarningById(id);
    }

    public int addTradeWarning(TradeWarning tradeWarning) {
        return tradeWarningMapper.addTradeWarning(tradeWarning);
    }

    public int updateTradeWarningById(TradeWarning tradeWarning) {
        return tradeWarningMapper.updateTradeWarningById(tradeWarning);
    }

    public void deleteTradeWarningById(Integer id) {
        tradeWarningMapper.deleteTradeWarningById(id);
    }

    public TradeWarning getTradeWarningByMarketId(Integer marketId, Integer type) {
        return tradeWarningMapper.getTradeWarningByMarketId(marketId, type);
    }
}
