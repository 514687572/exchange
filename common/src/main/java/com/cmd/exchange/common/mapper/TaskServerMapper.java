package com.cmd.exchange.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TaskServerMapper {
    /**
     * 获取制定任务的主服务器
     * 超过5分钟没有刷新就不是主服务器了
     *
     * @param taskName 任务名
     * @return 主服务器地址或者为NULL
     */
    @Select("select server_address from t_task_server where task_name=#{taskName}")
    String getTaskMasterServer(String taskName);

    /**
     * 更新任务主服务器的时间
     * 如果address的机器变成主服务器，返回1，否则0
     *
     * @param taskName 任务名
     * @param address  希望变成主服务器的地址
     * @return 主服务器返回1，其它返回0
     */
    @Update("update t_task_server set server_address=#{address},last_update_time=unix_timestamp()" +
            " where task_name=#{taskName} and (server_address=#{address} or unix_timestamp() - last_update_time>10)")
    int updateTaskMasterServer(@Param("taskName") String taskName, @Param("address") String address);
}
