package com.cmd.exchange.admin.dao;

import com.cmd.exchange.admin.vo.ReferralRewardVO;
import com.cmd.exchange.admin.vo.RegisterRewardReqVO;
import com.cmd.exchange.admin.vo.RegisterRewardVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Date;

@Mapper
//起名字，避免和api中同名的类冲突
@Component(value = "userBillMapper_admin")
public interface UserBillMapper {
    Page<RegisterRewardVO> getUserRegisterReward(RegisterRewardReqVO req, RowBounds rowBounds);

    Page<ReferralRewardVO> getUserReferralRewardVO(RegisterRewardReqVO req, RowBounds rowBounds);

    Page<RegisterRewardVO> getTradeRewardRec(@Param("userId") Integer userId, @Param("coinName") String coinName,
                                             @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, RowBounds rowBounds);
}
