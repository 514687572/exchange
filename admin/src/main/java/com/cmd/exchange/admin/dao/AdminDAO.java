package com.cmd.exchange.admin.dao;

import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.model.AdminToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据操作类
 * Created by jerry on 2017/12/21.
 */
@Mapper
public interface AdminDAO {

    void add(AdminEntity entity);

    int updatePassword(@Param("id") Integer id, @Param("password") String password, @Param("salt") String salt);

    AdminEntity getById(@Param("id") Integer id);

    AdminEntity getByAccount(@Param("userName") String account);

    List<AdminEntity> getUserList();

    void delete(@Param("userId") String userId);

    AdminToken getAdminTokenByToken(@Param("token") String token);

    AdminToken getAdminTokenByUserId(@Param("userId") Integer userId);

    void updateAdminToken(AdminToken token);

    void addAdminToken(AdminToken token);


}
