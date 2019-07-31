package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DispatchJob;
import com.cmd.exchange.common.model.DispatchLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DispatchLogMapper {
    int add(DispatchLog dispatchLog);

    Page<DispatchLog> getDispatchLog(@Param("userId") Integer userId, RowBounds rowBounds);
}
