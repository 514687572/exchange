package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.ChangeCoin;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ChangeCoinMapper {

    @Insert("insert into t_change_coin(want_change_time, coin_name, user_id, status, change_available, change_freeze, reason, comment, create_time)"
            + "values(#{wantChangeTime},#{coinName},#{userId},#{status},#{changeAvailable},#{changeFreeze},#{reason},#{comment},NOW())")
    int addChangeCoin(ChangeCoin changeCoin);

    @Select("select id from t_change_coin  where status=0 and want_change_time<=#{beforeTime} limit 0,#{maxNum}")
    List<Long> getWaitChangeIds(@Param("beforeTime") Date beforeTime, @Param("maxNum") int maxNum);

    @Select("select * from t_change_coin where id=#{id} for update")
    ChangeCoin lockChangeCoin(@Param("id") long id);

    // 设置为已经修改
    @Select("update t_change_coin set status=1,update_time=NOW() where id=#{id}")
    ChangeCoin markChanged(@Param("id") long id);

    @Delete("delete from t_change_coin where status=1 and want_change_time<=#{beforeTime}")
    int delOldChangeCoin(@Param("beforeTime") Date beforeTime);
}
