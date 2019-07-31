package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.AirdropMapper;
import com.cmd.exchange.common.mapper.InvestIncomeConfigMapper;
import com.cmd.exchange.common.model.Airdrop;
import com.cmd.exchange.common.model.InvestIncomeConfig;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.AirdropVO;
import com.cmd.exchange.common.vo.InvestIncomeConfigVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AirdropService {
    @Autowired
    private AirdropMapper airdropMapper;

    @Transactional
    public int addAirdrop(AirdropVO airdropVo) {
        airdropVo.setCreateTime(new Date());
        airdropVo.setUpdateTime(new Date());
        return airdropMapper.insertSelective(airdropVo.toModel());
    }

    public void deleteAirdrop(Integer id) {
        int count = airdropMapper.deleteByPrimaryKey(id);
        Assert.check(count == 0, ErrorCode.ERR_RECORD_NOT_EXIST);
    }

    @Transactional
    public void closeAirdrop(Integer id) {
        Airdrop a = airdropMapper.selectByPrimaryKey(id);
        Assert.check(a == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        a.setUpdateTime(new Date());
        a.setStatus(3);
        airdropMapper.updateByPrimaryKeySelective(a);
    }

    @Transactional
    public void updateAirdrop(AirdropVO airdropVO) {
        Airdrop a = airdropMapper.selectByPrimaryKey(airdropVO.getId());
        Assert.check(a == null, ErrorCode.ERR_RECORD_NOT_EXIST);
        a.setUpdateTime(new Date());
        a.setStatus(airdropVO.getStatus());
        a.setType(airdropVO.getType());
        a.setIntegrationMin(airdropVO.getIntegrationMin());
        a.setIntegrationMax(airdropVO.getIntegrationMax());
        a.setAirdropNum(airdropVO.getAirdropNum());
        airdropMapper.updateByPrimaryKeySelective(a);
    }

    public List<Airdrop> getAirdropList(Integer pageNo, Integer pageSize) {
        return airdropMapper.getAirdropList(new RowBounds(pageNo, pageSize));
    }

    public Airdrop getAirdropById(Integer id) {
        return airdropMapper.selectByPrimaryKey(id);
    }
}
