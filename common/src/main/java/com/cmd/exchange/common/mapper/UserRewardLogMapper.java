package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserBill;
import com.cmd.exchange.common.model.UserRewardLog;
import com.cmd.exchange.common.vo.RewardLogVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;

@Mapper
public interface UserRewardLogMapper {

    int add(UserRewardLog log);

    int updateUserRewardLogStatus(@Param("userId") int userId, @Param("from") int from, @Param("status") int status);

    BigDecimal getTotalUserReward(@Param("userId") int userId, @Param("coinName") String coinName,
                                  @Param("status") Integer status, @Param("reason") String[] reason);

    UserRewardLog getUserRewardByUserIdAndFrom(@Param("userId") int userId, @Param("from") int from);

    UserRewardLog getUserRegisterRewardByUserId(@Param("userId") int userId);


    Page<UserRewardLog> getUserRewardList(@Param("userId") int userId, @Param("coinName") String coinName,
                                          @Param("status") Integer status, @Param("reason") String[] reason, RowBounds rowBounds);

    Page<RewardLogVO> getUserRewardLog(@Param("userId") int userId, RowBounds rowBounds);
}
