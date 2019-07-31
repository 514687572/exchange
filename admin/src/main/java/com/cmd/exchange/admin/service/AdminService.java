package com.cmd.exchange.admin.service;

import com.cmd.exchange.admin.dao.AdminDAO;
import com.cmd.exchange.admin.dao.AdminLoginDAO;
import com.cmd.exchange.admin.dao.AuthorityDao;
import com.cmd.exchange.admin.model.AdminEntity;
import com.cmd.exchange.admin.model.AdminResourceEntity;
import com.cmd.exchange.admin.model.AdminToken;
import com.cmd.exchange.admin.model.RoleResEntity;
import com.cmd.exchange.admin.vo.AddAdminVO;
import com.cmd.exchange.admin.vo.LoginInfoVO;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.UserToken;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.utils.TokenGeneratorUtil;
import com.google.common.collect.ArrayListMultimap;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 用户业务实现类
 * Created by jerry on 2017/12/21.
 */
@Service
public class AdminService {
    private static Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Value("${token.expireTime:3600}")
    private int TOKENEXPIRETIME;

    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private AdminLoginDAO loginDAO;
    @Autowired
    private AuthorityDao authorityDao;
    @Autowired
    private AuthorityService authorityService;


    @Transactional
    public void add(AddAdminVO adminVO) {
        Assert.checkParam(adminVO.getUserName() == null, 1, "账号不能为空");
        Assert.checkParam(adminVO.getPassword() == null, 1, "密码不能为空");
        Assert.check(this.adminDAO.getByAccount(adminVO.getUserName()) != null, 1, "用户名已经被占用，请重新输入");

        //添加用户
        AdminEntity entity = adminVO.getUserEntity(1l);
        this.adminDAO.add(entity);
        //添加用户与角色关系
        authorityDao.addAdminRole(entity.getId(), adminVO.getRoleId());
    }

    /*public void updatePassword(String oldPassword, String newPassword) {
        AdminEntity entity = this.adminDAO.getById(currentAdmin.getId());

        Assert.check(!EncryptionUtil.check(oldPassword, entity.getPassword(), entity.getSalt()), 1,"原密码输入错误");

        String[] pwd = EncryptionUtil.getEncryptPassword(newPassword);
        this.adminDAO.updatePassword(entity.getId(), pwd[0], pwd[1]);
    }*/

    @Transactional
    public LoginInfoVO login(String username, String password) {

        Assert.checkParam(username == null, 1, "用户名不能为空");
        Assert.checkParam(password == null, 1, "密码不能为空");

        AdminEntity entity = this.adminDAO.getByAccount(username);
        Assert.check(entity == null, 1, "账号不存在");

//        todo本地暂时去掉校验
        Assert.check(!EncryptionUtil.check(password, entity.getPassword(), entity.getSalt()), 1, "用户名或密码错误，请重新输入");
        Assert.check("1".equals(entity.getStatus()), 1, "账号已禁用,联系管理员启用后再登录");

        //生成token
        AdminToken token = new AdminToken().setUserId(entity.getId()).setToken(TokenGeneratorUtil.generateValue())
                .setExpireTime(new Date(new Date().getTime() + TOKENEXPIRETIME * 1000));

        if (adminDAO.getAdminTokenByUserId(entity.getId()) == null) {
            adminDAO.addAdminToken(token);
        } else {
            adminDAO.updateAdminToken(token);
        }

        return new LoginInfoVO(entity, token.getToken());
    }

    public void logout(String accessToken) {

        //this.loginDAO.updateLogout();
    }

    public AdminEntity getUserById(Integer id) {
        AdminEntity entity = this.adminDAO.getById(id);
        if (entity == null) {
            return null;
        }

        return entity;
    }

    public AdminEntity getUserByUsername(String username) {
        AdminEntity entity = this.adminDAO.getByAccount(username);
        if (entity == null) {
            return null;
        }

        return entity;
    }

    public List<AdminEntity> getUserList() {
        List<AdminEntity> adminList = adminDAO.getUserList();
        if (adminList.isEmpty()) {
            return new ArrayList<>();
        }

        List<RoleResEntity> roleResVOList = authorityDao.getAllRoleResList();

        ArrayListMultimap<Integer, Integer> roleId2ResourceIdListMap = ArrayListMultimap.create();
        for (RoleResEntity r : roleResVOList) {
            roleId2ResourceIdListMap.put(r.getRoleId(), r.getResId());
        }


        List<AdminResourceEntity> resourceList = authorityDao.getResourceList();
        resourceList.sort(new Comparator<AdminResourceEntity>() {
            @Override
            public int compare(AdminResourceEntity o1, AdminResourceEntity o2) {
                if (o1.getParentId() > o2.getParentId()) {
                    return 1;
                } else if (o1.getParentId() < o2.getParentId()) {
                    return -1;
                }

                return o1.getId() - o2.getId();
            }
        });

        for (AdminEntity a : adminList) {
            List<Integer> resourceIdList = roleId2ResourceIdListMap.get(a.getRoleId());
            if (resourceIdList != null) {
                a.setResName(resourceNames(resourceIdList, resourceList));
            }
        }

        return adminList;
    }


    private String resourceNames(List<Integer> resourceIdList, List<AdminResourceEntity> resourceList) {
        String names = "";
        for (AdminResourceEntity v : resourceList) {
            if (resourceIdList.contains(v.getId())) {
                names += v.getName() + ",";
            }
        }
        return names.length() > 0 ? names.substring(0, names.length() - 1) : "";
    }

    @Transactional
    public void deleteUser(String[] userIds) {
        for (String userId : userIds) {
            adminDAO.delete(userId);
        }
    }

    public AdminToken getAdminByToken(String token) {
        return adminDAO.getAdminTokenByToken(token);
    }

    public int checkToken(String token) {
        AdminToken adminToken = getAdminByToken(token);
        if (adminToken == null) {
            return ErrorCode.ERR_TOKEN_NOT_EXIST;
        }

        if (adminToken.getExpireTime().before(new Date())) {
            return ErrorCode.ERR_TOKEN_EXPIRE_TIME;
        }

        //合法的token
        return 0;
    }

    @Transactional
    public void updatePassword(Integer userId, String userName, String newPassword) {
        AdminEntity user = adminDAO.getByAccount(userName);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        String[] passwordAndSalt = EncryptionUtil.getEncryptPassword(newPassword);
        int rows = adminDAO.updatePassword(user.getId(), passwordAndSalt[0], passwordAndSalt[1]);
        Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
    }

    @Transactional
    public void resetPassword(Integer userId) {
        AdminEntity user = adminDAO.getById(userId);
        Assert.check(user == null, ErrorCode.ERR_USER_NOT_EXIST);

        String[] passwordAndSalt = EncryptionUtil.getEncryptPassword("123456");
        int rows = adminDAO.updatePassword(userId, passwordAndSalt[0], passwordAndSalt[1]);
        Assert.check(rows == 0, ErrorCode.ERR_RECORD_UPDATE);
    }


}
