package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface UserLogMapper {

    @Options(useGeneratedKeys = true)
    @Insert("insert into t_user_log(user_id,type,operation_time,operation_ip,used_time,success,comment)" +
            "values(#{userId},#{type},#{operationTime},#{operationIp},#{usedTime},#{success},#{comment})")
    int insertUserLog(UserLog userLog);

    Page<UserLog> getUserLog(@Param("userId") int userId, @Param("array") String[] types, RowBounds rowBounds);
}
