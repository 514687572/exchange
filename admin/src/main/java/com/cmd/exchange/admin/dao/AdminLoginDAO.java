package com.cmd.exchange.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户登录会话信息
 * Created by jerry on 2018/1/2.
 */
@Mapper
public interface AdminLoginDAO {

    void add(@Param("id") String id, @Param("userId") String userId, @Param("token") String token, @Param("expire") int expire);

    int updateLogin(@Param("userId") String userId, @Param("token") String token, @Param("expire") int expire);

    int updateLogout(@Param("userId") String userId);

}
