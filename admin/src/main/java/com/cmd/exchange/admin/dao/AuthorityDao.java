package com.cmd.exchange.admin.dao;

import com.cmd.exchange.admin.model.AdminResourceEntity;
import com.cmd.exchange.admin.model.AuthorityEntity;
import com.cmd.exchange.admin.model.RoleEntity;
import com.cmd.exchange.admin.model.RoleResEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/6/4.
 */
@Mapper
public interface AuthorityDao {

    /**
     * 获取权限列表
     *
     * @param resLevel
     * @param preId
     * @return
     */
    List<AuthorityEntity> getAuthority(@Param("resLevel") Integer resLevel, @Param("preId") Integer preId);

    /**
     * 添加角色
     *
     * @param name
     * @param creator
     * @param des
     * @return
     */
    int addRole(@Param("name") String name, @Param("creator") String creator, @Param("des") String des);

    /**
     * 获取角色列表
     *
     * @return
     */
    List<RoleEntity> getRoleList();

    RoleEntity getRoleByName(@Param("name") String name);

    /**
     * 编辑角色
     *
     * @param name
     * @param des
     * @param roleId
     * @return
     */
    int updateRole(@Param("name") String name,
                   @Param("des") String des,
                   @Param("roleId") Integer roleId,
                   @Param("status") Integer status);


    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    int delRole(@Param("roleId") Integer roleId);

    /**
     * 添加角色与资源的关系
     *
     * @return
     */
    int addRoleRes(@Param("list") List<RoleResEntity> list);

    /**
     * 获取角色与资源的关系列表
     *
     * @param roleId
     * @return
     */
    List<RoleResEntity> getRoleResList(@Param("roleId") Integer roleId);

    /**
     * 删除角色与资源的关系
     *
     * @param roleId
     * @return
     */
    int delRoleRes(@Param("roleId") Integer roleId);

    /**
     * 添加用户与角色关系
     *
     * @param userId
     * @param roleId
     * @return
     */
    int addAdminRole(@Param("userId") Integer userId, @Param("roleId") Long roleId);


    List<AdminResourceEntity> getResourceList();

    List<RoleResEntity> getAllRoleResList();
}
