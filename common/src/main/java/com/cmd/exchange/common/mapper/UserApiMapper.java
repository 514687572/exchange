package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.UserApi;
import com.cmd.exchange.common.vo.UserApiVO;
import com.cmd.exchange.common.vo.UserApiVOReqVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface UserApiMapper {
    int addApiKey(UserApi api);

    UserApiVO getApiById(@Param("id") Integer id);

    int deleteApiById(@Param("id") Integer id);

    int updateUserApiById(UserApi api);

    UserApiVO getUserApiByApiKey(@Param("apiKey") String apiKey);

    Page<UserApiVO> getUserApiList(UserApiVOReqVO req, RowBounds rowBounds);

    int updateUserApiStatus(@Param("idList") List<Integer> idList, @Param("status") Integer status);

    void delUserApiById(@Param("id") Integer id);
}
