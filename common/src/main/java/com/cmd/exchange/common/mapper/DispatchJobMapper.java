package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DispatchJob;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DispatchJobMapper {
    int add(DispatchJob dispatchJob);

    List<DispatchJob> getDispatchJob(@Param("jobId") Integer jobId);

    int freeDispatch(@Param("id") Integer id, @Param("amount") BigDecimal amount);

    Page<DispatchJob> getDispatchJobList(@Param("realName") String realName, @Param("userName") String userName, @Param("coinName") String coinName, RowBounds rowBounds);
}
