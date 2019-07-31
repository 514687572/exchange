package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.CronConfig;
import com.cmd.exchange.common.model.DispatchConfig;
import com.cmd.exchange.common.model.DispatchJob;
import com.cmd.exchange.common.model.DispatchLog;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.DispatchConfigVo;
import com.cmd.exchange.common.vo.DispatchReqVO;
import com.cmd.exchange.common.vo.DispatchVO;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Dispatch;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DispatchService {

    @Autowired
    CronConfigMapper cronConfigMapper;
    @Autowired
    DispatchConfigMapper dispatchConfigMapper;
    @Autowired
    DispatchJobMapper dispatchJobMapper;
    @Autowired
    DispatchLogMapper dispatchLogMapper;
    @Autowired
    UserCoinMapper userCoinMapper;
    @Autowired
    UserCoinService userCoinService;

    //定时任务配置
    public List<CronConfig> getCronConfigList() {
        return cronConfigMapper.getCronConfigList();
    }

    //获取拨币配置
    public List<DispatchConfigVo> getDispatchConfigList() {
        return dispatchConfigMapper.getDispatchConfig();
    }

    //通过id获取锁仓配置信息
    public DispatchConfigVo getDispatchInfoById(Integer id) {
        return dispatchConfigMapper.getDispatchConfigInfoById(id);
    }

    //添加拨币配置
    public void addDispatchConfig(DispatchConfig dispatchConfig) {
        Integer cronId = dispatchConfig.getCronId();
        Assert.check(cronConfigMapper.getCronConfig(cronId) == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        dispatchConfigMapper.add(dispatchConfig);
    }

    public void adminAddDispatchConfig(DispatchConfigVo dispatchConfigVo) {
        Integer cronId = dispatchConfigVo.getCronId();
        Assert.check(cronConfigMapper.getCronConfig(cronId) == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        dispatchConfigMapper.adminAdd(dispatchConfigVo);
    }

    //修改拨币配置信息
    public void modDispatchConfig(DispatchConfig dispatchConfig) {
        Assert.check(dispatchConfigMapper.getDispatchConfigById(dispatchConfig.getId()) == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        dispatchConfigMapper.mod(dispatchConfig);
    }

    //修改拨币配置信息
    public void updateDispatchConfig(DispatchConfigVo dispatchConfigVo) {
        Assert.check(dispatchConfigMapper.getDispatchConfigById(dispatchConfigVo.getId()) == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        dispatchConfigMapper.updateDispatchConfig(dispatchConfigVo);
    }

    //删除锁仓配置
    public void delDispatchConfig(Integer id) {
        dispatchConfigMapper.del(id);
    }

    //拨币
    @Transactional
    public void dispatch(DispatchReqVO dispatchReqVO) {
        String comment = dispatchReqVO.getComment();
        Integer dispatchId = dispatchReqVO.getDispatchId();
        DispatchConfig dispatchConfig = dispatchConfigMapper.getDispatchConfigById(dispatchId);
        Assert.check(dispatchConfig == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        //拨币
        for (DispatchVO dis : dispatchReqVO.getList()) {
            //拨币，得到lock
            BigDecimal lockAmount = dis.getAmount().multiply(dispatchConfig.getLockRate());
            //首次到账值
            BigDecimal amount = dis.getAmount().multiply(new BigDecimal(1 - dispatchConfig.getLockRate().doubleValue()));
            userCoinService.changeUserCoin(dis.getUserId(), dis.getCoinName(), amount, BigDecimal.ZERO, UserBillReason.DISPATCH_RELEASE, "拨币释放");
            dispatchLogMapper.add(new DispatchLog().setAmount(amount).setUserId(dis.getUserId()).setMobile(dis.getMobile()).setCoinName(dis.getCoinName()));
            if (dis.getAmount().doubleValue() > 0) {
                DispatchJob dispatchJob = new DispatchJob().setAmount(lockAmount).setAmountAll(dis.getAmount())
                        .setCoinName(dis.getCoinName()).setLockRate(dispatchConfig.getLockRate()).setFreeRate(dispatchConfig.getFreeRate())
                        .setMobile(dis.getMobile()).setUserId(dis.getUserId()).setComment(comment)
                        .setCronId(dispatchConfig.getCronId()).setCronName(dispatchConfig.getCronName());
                dispatchJobMapper.add(dispatchJob);
            }
        }
    }

    //获取拨币记录job
    public Page<DispatchJob> getDispatchJob(String realName, String userName, String coinName, Integer pageNo, Integer pageSize) {
        return dispatchJobMapper.getDispatchJobList(realName, userName, coinName, new RowBounds(pageNo, pageSize));
    }

    //释放记录日志
    public Page<DispatchLog> getDispatchLog(Integer userId, Integer pageNo, Integer pageSize) {
        return dispatchLogMapper.getDispatchLog(userId, new RowBounds(pageNo, pageSize));
    }

    //拨币 拨到锁仓/转入冻结金里面
    @Transactional
    public void changeRfCoin(DispatchReqVO dispatchReqVO) {
        //拨币
        for (DispatchVO dis : dispatchReqVO.getList()) {
            BigDecimal amount = dis.getAmount();
            userCoinService.changeUserReceivedFreezeCoin(dis.getUserId(), dis.getCoinName(), amount, UserBillReason.DISPATCH_RELEASE, "拨币释放");
        }
    }


}
