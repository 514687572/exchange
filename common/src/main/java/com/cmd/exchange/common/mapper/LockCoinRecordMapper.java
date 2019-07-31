package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.LockCoinRecord;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

@Mapper
public interface LockCoinRecordMapper {

    @Options(useGeneratedKeys = true)
    @Insert("insert into t_lock_coin_record(lock_no,user_id,coin_name,lock_rate,lock_amount,release_rate,release_amount,cron_name,cron_expression,create_time)" +
            "values(#{lockNo},#{userId},#{coinName},#{lockRate},#{lockAmount},#{releaseRate},#{releaseAmount},#{cronName},#{cronExpression},NOW())")
    int insertRecord(LockCoinRecord record);

    @Select({"<script>",
            "select * from t_lock_coin_record where 1=1 ",
            "<if test='userId != null'> and user_id=#{userId} </if>",
            "<if test='coinName != null and coinName.length > 0'> and coin_name=#{coinName} </if>",
            "<if test='lockNo != null and lockNo.length > 0'> and lock_no=#{lockNo} </if>",
            "<if test='beginTime != null and beginTime.length > 0'> and create_time >= #{beginTime} </if>",
            "<if test='endTime != null and endTime.length > 0'> #{endTime} > create_time </if>",
            " order by id desc",
            "</script>"})
    Page<LockCoinRecord> findRecord(@Param("userId") Integer userId, @Param("coinName") String coinName, @Param("lockNo") String lockNo,
                                    @Param("beginTime") String beginTime, @Param("endTime") String endTime, RowBounds rowBounds);
}
