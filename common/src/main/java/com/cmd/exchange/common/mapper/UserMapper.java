package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserGroupConfig;
import com.cmd.exchange.common.model.UserToken;
import com.cmd.exchange.common.vo.ReferrerVO;
import com.cmd.exchange.common.vo.TopologyVO;
import com.cmd.exchange.common.vo.UserInfoVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    int addUserToken(UserToken userToken);

    int updateUserToken(UserToken userToken);

    UserToken getUserTokenByUserId(@Param("userId") int userId);

    UserToken getUserTokenByToken(@Param("token") String token);

    int disableUserToken(@Param("userId") int userId, @Param("expireTime") Date expireTime);

    int addUser(User user);

    //TODO: 电话号码可能不是唯一的，后续应该都改成根据区号+号码查询
    User getUserByMobile(@Param("mobile") String mobile);

    List<User> adminGetUserByUserName(@Param("userName") List<String> userName);

    User getUserByMobileAndAreaCode(@Param("areaCode") String areaCode, @Param("mobile") String mobile);

    User getUserByUserName(@Param("userName") String userName);

    User getUserByRealName(@Param("realName") String realName);

    User getUserByUserId(@Param("userId") int userId);

    User getUserByEmail(@Param("email") String email);

    User getUserByIDcard(@Param("idcard") String idcard);

    List<User> getUsersByIDcard(@Param("idcard") String idcard, @Param("maxCount") int maxCount);

    int updateUserByUserId(User user);

    int updateUserIdCard(User user);

    int updateUserLoginLogByUserId(@Param("id") int userId, @Param("lastLoginIp") String lastLoginIp);

    Integer getUserIdByInviteCode(@Param("inviteCode") String inviteCode);

    int getUserReferrerCount(@Param("userId") int userId);

    Page<UserInfoVO> getUserList(@Param("referrer") Integer referrer,
                                 @Param("mobile") String mobile,
                                 @Param("idCardStatus") Integer idCardStatus,
                                 @Param("status") Integer status,
                                 @Param("groupType") String groupType,
                                 @Param("email") String email,
                                 @Param("realName") String realName,
                                 @Param("integrationMin") BigDecimal integrationMin,
                                 @Param("integrationMax") BigDecimal integrationMax,
                                 RowBounds rowBounds);

    Integer getUserReferrerKYCCount(@Param("userId") Integer userId);

    List<UserInfoVO> getUserListByIdList(@Param("idList") List<Integer> idList);

    Page<ReferrerVO> getUserReferrer(@Param("userId") Integer userId, RowBounds rowBounds);

    int updateGroupType(@Param("userId") Integer userId, @Param("groupType") String groupType);

    int getUserListByGroupTypeCount(@Param("groupType") String groupType);

    List<TopologyVO> getTopologyByUserId(@Param("userId") Integer userId);

    int getSubordinateBool(@Param("userId") Integer userId);

    UserGroupConfig getUserGroupTypeName(@Param("id") Integer userId);

    // 根据用户组类型获取所有用户，这里只返回用户ID和用户名，不返回其它字段
    List<User> getAllUserIdNameByGroupType(@Param("groupType") String groupType);

    // 根据用户id获取用户的推荐用户个数
    int getRecUserCountByInviteCode(@Param("referrer") int referrer);

    // 根据用户id获取用户的二级推荐用户个数
    int getRec2UserCountByInviteCode(@Param("referrer") int referrer);

    @Select("select id from t_user where user_type=1")
    List<Integer> getAllSuperUserIds();

    @Select("select id from t_user where user_type=#{nodeId}")
    List<Integer> getUserIdsByNodeId(@Param("nodeId") int nodeId);

    /**
     * 获取用户可用的币种余额
     */
    BigDecimal getAvailableBalance(@Param("mobile") String mobile, @Param("coinName") String coinName);

    @Update("update t_user set image = #{image} where id = #{userId}")
    int modifyAvatar(@Param("userId") Integer userId, @Param("image") String image);

    List<User> getAll();
}
