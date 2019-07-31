package com.cmd.exchange.service;

import com.cmd.exchange.common.mapper.UserGroupConfigMapper;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserGroupConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserGroupConfigService {
    @Autowired
    private UserGroupConfigMapper userGroupConfigMapper;

    @Transactional
    public int updateUserGroupConfigNameById(UserGroupConfig userGroupConfig) {
        return userGroupConfigMapper.updateUserGroupConfigNameById(userGroupConfig);
    }

    @Transactional
    public int updateUserGroupConfigStatusById(UserGroupConfig userGroupConfig) {
        return userGroupConfigMapper.updateUserGroupConfigStatusById(userGroupConfig);
    }

    public List<UserGroupConfig> getUserGroupConfigList() {
        return userGroupConfigMapper.getUserGroupConfigList();
    }

    public UserGroupConfig getUserGroupConfigById(Integer id) {
        return userGroupConfigMapper.getUserGroupConfigById(id);
    }

    @Transactional
    public int addUserGroupConfig(UserGroupConfig userGroupConfig) {
        return userGroupConfigMapper.addUserGroupConfig(userGroupConfig);
    }

    @Transactional
    public void delUserGroupConfig(Integer id) {
        userGroupConfigMapper.delUserGroupConfigById(id);
    }
}
