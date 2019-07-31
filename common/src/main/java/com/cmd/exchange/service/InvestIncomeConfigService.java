package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.InvestIncomeConfigMapper;
import com.cmd.exchange.common.model.InvestIncomeConfig;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class InvestIncomeConfigService {
    @Autowired
    private InvestIncomeConfigMapper investIncomeConfigMapper;

    @Transactional
    public int addInvestIncomeConfig(InvestIncomeConfigVO investIncomeConfigVo) {
        investIncomeConfigVo.setCreateTime(new Date());
        investIncomeConfigVo.setUpdateTime(new Date());
        return investIncomeConfigMapper.insertSelective(investIncomeConfigVo.toModel());
    }

    public void deleteInvestIncomeConfig(Integer id) {
        int count = investIncomeConfigMapper.deleteByPrimaryKey(id);
        Assert.check(count == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
    }

    @Transactional
    public void updateInvestIncomeConfig(InvestIncomeConfigVO investIncomeConfigVO) {
        InvestIncomeConfig investIncomeConfig = investIncomeConfigMapper.selectByPrimaryKey(investIncomeConfigVO.getId());
        Assert.check(investIncomeConfig == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        investIncomeConfig.setStaticIncom(investIncomeConfigVO.getStaticIncom());
        investIncomeConfig.setDynamicIncom(investIncomeConfigVO.getDynamicIncom());
        investIncomeConfig.setDynamicScale(investIncomeConfigVO.getDynamicScale());
        investIncomeConfig.setUpdateTime(new Date());
        investIncomeConfigMapper.updateByPrimaryKeySelective(investIncomeConfig);
    }

    public List<InvestIncomeConfigVO> getInvestIncomeConfigList(Integer type, Integer pageNo, Integer pageSize) {
        return investIncomeConfigMapper.getInvestIncomeConfigList(type,new RowBounds(pageNo, pageSize));
    }
}
