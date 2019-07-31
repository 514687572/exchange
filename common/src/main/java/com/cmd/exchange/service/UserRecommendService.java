package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.UserIntegrationMapper;
import com.cmd.exchange.common.mapper.UserMapper;
import com.cmd.exchange.common.mapper.UserRecommendMapper;
import com.cmd.exchange.common.mapper.UserTeamMapper;
import com.cmd.exchange.common.model.*;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.CageUtil;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.common.vo.UserRecIntegr;
import com.cmd.exchange.common.vo.UserRecIntegrVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRecommendService {
    @Autowired
    private UserRecommendMapper userRecommendMapper;
    @Autowired
    private UserIntegrationMapper userIntegrationMapper;
    @Autowired
    private UserTeamMapper userTeamMapper;

    @Autowired
    private UserMapper userMapper;




    public UserRecIntegrVo getUserChilByUserId(User user){
        UserRecIntegrVo userRecIntegrVo = new UserRecIntegrVo();

        userRecIntegrVo.setPid(user.getReferrer());
        BigDecimal userIntegrationByUserId = userIntegrationMapper.getUserIntegrationByUserId(user.getReferrer());
        if(null != userIntegrationByUserId){
            userRecIntegrVo.setPIntegration(userIntegrationByUserId);
        }


        UserRecommendExample userRecommendExample = new UserRecommendExample();
        UserRecommendExample.Criteria userRecommendCriteria = userRecommendExample.createCriteria();
        userRecommendCriteria.andPid1EqualTo(user.getId());
        int recUnm = userRecommendMapper.countByExample(userRecommendExample);
        userRecIntegrVo.setRecNum(recUnm);

        UserTeamExample userTeamExample = new UserTeamExample();
        UserTeamExample.Criteria userTeamCriteria = userTeamExample.createCriteria();
        userTeamCriteria.andUserIdEqualTo(user.getId());
        int teamNum = userTeamMapper.countByExample(userTeamExample);
        userRecIntegrVo.setTeamNum(teamNum);


        List<UserRecommend> userRecommends = userRecommendMapper.selectByExample(userRecommendExample);
        List<UserRecIntegr> userRecIntegrs = new ArrayList<>();
        userRecIntegrVo.setRecIntegrList(userRecIntegrs);
        userRecommends.stream().forEach(chil ->{

            UserRecIntegr userRecIntegr = new UserRecIntegr();
            userRecIntegr.setUserId(chil.getUserId());
            BigDecimal userIntegrationByUserId1 = userIntegrationMapper.getUserIntegrationByUserId(chil.getUserId());
            if(null != userIntegrationByUserId1)
            {
                userRecIntegr.setIntegration(userIntegrationByUserId1);
            }
            userRecIntegrs.add(userRecIntegr);
        });
        return userRecIntegrVo;
    }
    public UserRecIntegrVo getUserChilByUserId(int userId){

        User user = userMapper.getUserByUserId(userId);
        UserRecIntegrVo userRecIntegrVo = new UserRecIntegrVo();

        userRecIntegrVo.setPid(user.getReferrer());
        userRecIntegrVo.setPIntegration(userIntegrationMapper.getUserIntegrationByUserId(user.getReferrer()));


        UserRecommendExample userRecommendExample = new UserRecommendExample();
        UserRecommendExample.Criteria userRecommendCriteria = userRecommendExample.createCriteria();
        userRecommendCriteria.andPid1EqualTo(user.getId());


        List<UserRecommend> userRecommends = userRecommendMapper.selectByExample(userRecommendExample);
        List<UserRecIntegr> userRecIntegrs = new ArrayList<>();
        userRecIntegrVo.setRecIntegrList(userRecIntegrs);
        userRecommends.stream().forEach(chil ->{

            UserRecIntegr userRecIntegr = new UserRecIntegr();
            userRecIntegr.setUserId(chil.getUserId());
            BigDecimal userIntegrationByUserId1 = userIntegrationMapper.getUserIntegrationByUserId(chil.getUserId());
            if(null != userIntegrationByUserId1)
            {
                userRecIntegr.setIntegration(userIntegrationByUserId1);
            }
            userRecIntegrs.add(userRecIntegr);
        });
        return userRecIntegrVo;
    }


    @Transactional
    public void userRecUser(int fromUserId, int userId){


        UserRecommend oldUserRecommend = userRecommendMapper.selectByPrimaryKey(fromUserId);

        UserRecommend newUserRecommend = new UserRecommend();
        UserTeam userTeam = new UserTeam();
        newUserRecommend.setUserId(userId);
        if(null != oldUserRecommend){
            newUserRecommend.setPid1(oldUserRecommend.getUserId());
            newUserRecommend.setPid2(oldUserRecommend.getPid1());
            newUserRecommend.setPid3(oldUserRecommend.getPid2());
            newUserRecommend.setPid4(oldUserRecommend.getPid3());
            newUserRecommend.setPid5(oldUserRecommend.getPid4());
            newUserRecommend.setPid6(oldUserRecommend.getPid5());
            newUserRecommend.setPid7(oldUserRecommend.getPid6());
            newUserRecommend.setPid8(oldUserRecommend.getPid7());
            newUserRecommend.setPid9(oldUserRecommend.getPid8());
            newUserRecommend.setPid10(oldUserRecommend.getPid9());

            //获取九级父级用户
            List<Integer> pids = getPids(oldUserRecommend,false);

            //统计父级用户下十级团队
            userTeam.setChildrenUserId(userId);
            pids.stream().filter(pid -> pid != null).forEach(pid ->{
                userTeam.setUserId(pid);
                userTeamMapper.insert(userTeam);
            });

            //统计当前用户上十级团队
            userTeam.setUserId(userId);
            pids.stream().filter(pid -> pid != null).forEach(pid ->{
                userTeam.setChildrenUserId(pid);
                userTeamMapper.insert(userTeam);
            });



        }else{

            newUserRecommend.setPid1(fromUserId);

            //用户下级团队成员统计
            userTeam.setUserId(fromUserId);
            userTeam.setChildrenUserId(userId);
            userTeamMapper.insert(userTeam);

            //用户上级团队成员统计
            userTeam.setUserId(userId);
            userTeam.setChildrenUserId(fromUserId);
            userTeamMapper.insert(userTeam);

        }

        userRecommendMapper.insertSelective(newUserRecommend);
    }


    /**
     * 作为测试用
     */
    public void userRecInit(){

        List<User> all = userMapper.getAll();

        for(User user:all){


            if(user.getReferrer() != null)
                userRecUser(user.getReferrer(),user.getId());


        }


    }

    /**
     * 层级动态
     * @param userRecommend
     * @param flse 可选层级
     * @return
     */
    private List<Integer> getPids(UserRecommend userRecommend,boolean flse) {
        List<Integer> ps = new ArrayList<>();
        if(null != userRecommend.getUserId())
        {
            ps.add(userRecommend.getUserId());
        }
        if(null != userRecommend.getPid1())
        {
            ps.add(userRecommend.getPid1());
        }
        if(null != userRecommend.getPid2())
        {
            ps.add(userRecommend.getPid2());
        }
        if(null != userRecommend.getPid3())
        {
            ps.add(userRecommend.getPid3());
        }
        if(null != userRecommend.getPid4())
        {
            ps.add(userRecommend.getPid4());
        }
        if(null != userRecommend.getPid5())
        {
            ps.add(userRecommend.getPid5());
        }
        if(null != userRecommend.getPid6())
        {
            ps.add(userRecommend.getPid6());
        }
        if(null != userRecommend.getPid7())
        {
            ps.add(userRecommend.getPid7());
        }
        if(null != userRecommend.getPid8())
        {
            ps.add(userRecommend.getPid8());
        }
        if(null != userRecommend.getPid9())
        {
            ps.add(userRecommend.getPid9());
        }
        if(null != userRecommend.getPid10() && flse)
        {
            ps.add(userRecommend.getPid10());
        }
        return ps;
    }

    /**
     *
     * @param recMobile  被推荐人，下级
     * @param newMobile   新插入
     * @param oldMobile   推荐人，上级
     */
    @Transactional
    public void intertUserRecommend(String recMobile,String newMobile,String oldMobile){
        User oldUser = userMapper.getUserByMobile(oldMobile);
//        User newUser = userMapper.getUserByMobile(newMobile);
        Assert.check(null == oldUser, ErrorCode.ERR_RECORD_NOT_EXIST);

        User user = new User();
        user.setUserName(newMobile);
        user.setMobile(newMobile);
        user.setReferrer(oldUser.getId());
        user.setAreaCode("86");
        String[] encryptedPassword = EncryptionUtil.getEncryptPassword(EncryptionUtil.MD5("a123456" + "5466f6e5160010ce5d9d7e0b7dd3420d"));
        user.setPassword(encryptedPassword[0]).setSalt(encryptedPassword[1]);
        user.setInviteCode(CageUtil.getWords(10));
        userMapper.addUser(user);

        user = userMapper.getUserByMobile(newMobile);

        User recUser = userMapper.getUserByMobile(recMobile);
        Assert.check(null == recUser, ErrorCode.ERR_RECORD_NOT_EXIST);

        UserRecommend recUserRecommend = userRecommendMapper.selectByPrimaryKey(recUser.getId());

        Assert.check(null == recUserRecommend, ErrorCode.ERR_RECORD_NOT_EXIST);
        Assert.check(recUserRecommend.getPid1().intValue() != oldUser.getId().intValue(), ErrorCode.ERR_RECORD_NOT_EXIST);



        recUser.setReferrer(user.getId());
        userMapper.updateUserByUserId(recUser);

        updateUserRecommend(recUser.getId(),user.getId(),oldUser.getId());
    }
    /**
     * 上下级推荐,插入更新
     * @param oldUserId 上级用户
     * @param newUserId  新用户
     * @param recUserId 下级用户
     */
    @Transactional
    public void updateUserRecommend(int recUserId,int newUserId,int oldUserId){

        //处理上级用户团队
        userRecUser(oldUserId,newUserId);

        //用户推荐人增补
        UserRecommend userRecommend = userRecommendMapper.selectByPrimaryKey(recUserId);

        if(null == userRecommend)return;

        UserRecommend newUserRecommend = getUserRecommend(newUserId, oldUserId, userRecommend);
        userRecommendMapper.updateByPrimaryKeySelective(newUserRecommend);
        //团队波动
        userTeamFluctuate(recUserId, newUserId, oldUserId,false);


        //处理下级用户团队
        supplementRecUser(newUserId, recUserId,oldUserId,0);

    }

    /**
     * 团队波动
     * @param recUserId  被推荐人  下级
     * @param newUserId  推荐人    新用户
     * @param oldUserId  推荐人    上级
     * @param isReduce
     */
    private void userTeamFluctuate(int recUserId, int newUserId, int oldUserId,boolean isReduce) {
        //用户团队增员
        UserTeam userTeam = new UserTeam();
        userTeam.setUserId(newUserId);
        userTeam.setChildrenUserId(recUserId);
        userTeamMapper.insert(userTeam);
        //用户团队减员
        if(isReduce)
        {
            UserTeamExample userTeamExample = new UserTeamExample();
            UserTeamExample.Criteria criteria = userTeamExample.createCriteria();
            criteria.andUserIdEqualTo(recUserId);
            criteria.andChildrenUserIdEqualTo(oldUserId);
            userTeamMapper.deleteByExample(userTeamExample);
        }
        //用户团增加
        userTeam.setChildrenUserId(newUserId);
        userTeam.setUserId(recUserId);
        userTeamMapper.insert(userTeam);
    }

    /**
     * 处理下级用户团队
     * @param newUserId  推荐用户
     * @param recUserId  被变更用户
     * @param temp
     */
    private void supplementRecUser(int newUserId, int recUserId,int oldUserId, int temp) {

        if(temp >= 9)return;
        temp ++;
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        UserRecommendExample.Criteria criteria = userRecommendExample.createCriteria();
        criteria.andPid1EqualTo(recUserId);
        List<UserRecommend> userRecommends = userRecommendMapper.selectByExample(userRecommendExample);

        if(userRecommends.isEmpty())return;
        //标识是否减员
        boolean isReduce = temp == 9 ? true : false;
        for(UserRecommend u:userRecommends){

            userTeamFluctuate(u.getUserId(),newUserId,oldUserId,isReduce);

            //变更用户推荐关系
            UserRecommend userRecommend = getUserRecommend(newUserId, oldUserId, u);
            userRecommendMapper.updateByPrimaryKeySelective(userRecommend);

            supplementRecUser(newUserId,u.getUserId(),oldUserId, temp);

        }

    }

    @NotNull
    private UserRecommend getUserRecommend(int newUserId, int oldUserId, UserRecommend u) {

        UserRecommend userRecommend = new UserRecommend();

        List<Integer> pids = getPids(u,true);
        List<Integer> addPids = new ArrayList<>();
        boolean flse = false;
        for(int i = 0; i< pids.size();i++){

            if(pids.get(i).intValue() == oldUserId && !flse){
                addPids.add(newUserId);
                flse = true;
                i = i - 1;
            }else{
                addPids.add(pids.get(i));
            }
        }
        for(int j=0;j<addPids.size();j++){
            if(j == 0){
                userRecommend.setUserId(addPids.get(j));
                continue;
            }
            if(j == 1){
                userRecommend.setPid1(addPids.get(j));
                continue;
            }
            if(j == 2){
                userRecommend.setPid2(addPids.get(j));
                continue;
            }
            if(j == 3){
                userRecommend.setPid3(addPids.get(j));
                continue;
            }
            if(j == 4){
                userRecommend.setPid4(addPids.get(j));
                continue;
            }
            if(j == 5){
                userRecommend.setPid5(addPids.get(j));
                continue;
            }
            if(j == 6){
                userRecommend.setPid6(addPids.get(j));
                continue;
            }
            if(j == 7){
                userRecommend.setPid7(addPids.get(j));
                continue;
            }
            if(j == 8){
                userRecommend.setPid8(addPids.get(j));
                continue;
            }
            if(j == 9){
                userRecommend.setPid9(addPids.get(j));
                continue;
            }
            if(j == 10){
                userRecommend.setPid10(addPids.get(j));
                continue;
            }
        }
        return userRecommend;
    }

}
