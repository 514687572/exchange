package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserGroupConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserGroupConfigMapper {

    List<UserGroupConfig> getUserGroupConfigList();

    int updateUserGroupConfigNameById(UserGroupConfig userGroupConfig);

    int updateUserGroupConfigStatusById(UserGroupConfig userGroupConfig);

    UserGroupConfig getUserGroupConfigById(@Param("id") Integer id);

    int addUserGroupConfig(UserGroupConfig UserGrouopConfig);

    void delUserGroupConfigById(@Param("id") Integer id);
}
