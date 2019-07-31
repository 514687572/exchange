package com.cmd.exchange.admin.service;

import com.cmd.exchange.admin.dao.AuthorityDao;
import com.cmd.exchange.admin.model.AdminResourceEntity;
import com.cmd.exchange.admin.model.AuthorityEntity;
import com.cmd.exchange.admin.model.RoleEntity;
import com.cmd.exchange.admin.model.RoleResEntity;
import com.cmd.exchange.common.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/6/4.
 */
@Service("authorityService")
public class AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    public Map list() {

        //获取一级菜单
        List<AuthorityEntity> firstList = authorityDao.getAuthority(1, null);
        for (AuthorityEntity l : firstList) {
            //查询二级菜单
            List<AuthorityEntity> secondList = authorityDao.getAuthority(2, l.getResId());
            for (AuthorityEntity s : secondList) {
                //查询三级菜单
                List<AuthorityEntity> thirdList = authorityDao.getAuthority(3, s.getResId());
                s.setThirdList(thirdList);
            }
            l.setSecondList(secondList);

        }
        Map map = new HashMap();
        map.put("firstList", firstList);
        return map;
    }

    @Transactional
    public void addRole(String name, Integer[] res, String creator) {
        RoleEntity oldEntity = authorityDao.getRoleByName(name);
        Assert.check(oldEntity != null, 1, "同名权限已经存在");
        int count = authorityDao.addRole(name, creator, "");
        Assert.check(count < 1, 1, "添加角色失败!");


        oldEntity = authorityDao.getRoleByName(name);

        setPermission(res, oldEntity.getRoleId());

    }

    public List<RoleEntity> getRoleList() {
        return authorityDao.getRoleList();
    }

    public void updateRole(String name, String des, Integer roleId, Integer status) {
        int count = authorityDao.updateRole(name, des, roleId, status);
        Assert.check(count < 1, 1, "更新角色失败!");
    }

    public void delRole(Integer roleId) {
        int count = authorityDao.delRole(roleId);
        Assert.check(count < 1, 1, "删除角色失败!");
    }

    public void setPermission(Integer[] resIds, Integer roleId) {
        //判断角色是否已存在资源关系
        List<RoleResEntity> resList = authorityDao.getRoleResList(roleId);
        if (resList.size() > 0) {
            //删除角色与原有的资源关系
            int count = authorityDao.delRole(roleId);
            Assert.check(count < 1, 1, "删除角色与资源关系失败!");
        }

        //添加角色与资源关系
        List<RoleResEntity> list = new ArrayList<>();

        for (int i = 0; i < resIds.length; i++) {
            RoleResEntity roleResVO = new RoleResEntity();
            roleResVO.setRoleId(roleId);
            roleResVO.setResId(resIds[i]);
            list.add(roleResVO);
        }

        int count = authorityDao.addRoleRes(list);
        Assert.check(count < 1, 1, "添加角色与资源关系失败");
    }

    public List<AdminResourceEntity> getResourceList(Integer roleId) {
        List<AdminResourceEntity> resourceList = authorityDao.getResourceList();

        Map<Integer, AdminResourceEntity> id2ResourceMap = resourceList.stream().collect(Collectors.toMap(AdminResourceEntity::getId, Function.identity()));


        for (AdminResourceEntity v : resourceList) {
            AdminResourceEntity parent = id2ResourceMap.get(v.getParentId());
            if (parent != null) {
                parent.addChild(v);
            }
        }


        if (roleId != null) {
            List<RoleResEntity> roleResVOList = authorityDao.getRoleResList(roleId);

            for (RoleResEntity v : roleResVOList) {
                AdminResourceEntity resourceVO = id2ResourceMap.get(v.getResId());
                if (resourceVO != null) {
                    resourceVO.setSelected(true);
                }
            }
        }

        List<AdminResourceEntity> topLevelVOList = new ArrayList<>();
        for (AdminResourceEntity v : resourceList) {
            if (v.getParentId().equals(0)) {
                topLevelVOList.add(v);
            }
        }

        return topLevelVOList;
    }
}
