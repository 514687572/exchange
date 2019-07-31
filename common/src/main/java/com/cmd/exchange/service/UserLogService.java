package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.OpLogType;
import com.cmd.exchange.common.mapper.UserLogMapper;
import com.cmd.exchange.common.model.UserLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogService {
    @Autowired
    UserLogMapper userLogMapper;

    public Page<UserLog> getLoginLog(int userId, int pageNo, int pageSize) {
        String[] logTypes = new String[]{OpLogType.OP_USER_LOGIN, OpLogType.OP_USER_LOGOUT};
        return userLogMapper.getUserLog(userId, logTypes, new RowBounds(pageNo, pageSize));
    }
}
