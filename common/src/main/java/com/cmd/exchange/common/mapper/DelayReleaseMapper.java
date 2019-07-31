package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DelayRelease;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 延迟释放冻结金的表，用于扣押金额（例如奖励金），到期后变成可用金
 */
@Mapper
public interface DelayReleaseMapper {
    @Insert("insert into t_delay_release(release_time,coin_name,user_id,amount,had_release,create_time)values("
            + "#{releaseTime},#{coinName},#{userId},#{amount},0,now())")
    int add(@Param("releaseTime") Date releaseTime, @Param("coinName") String coinName,
            @Param("userId") int userId, @Param("amount") BigDecimal amount);

    @Select("select * from t_delay_release where release_time<#{releaseTime} and had_release=0 limit 0,1 for update")
    DelayRelease lockOneRecord(@Param("releaseTime") Date releaseTime);

    @Update("update t_delay_release set had_release=1 where id=#{id}")
    int releaseRecord(@Param("id") int id);
}
