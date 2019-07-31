package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.OtcKline;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;

@Mapper
public interface OtcKlineMapper {

    int add(OtcKline applicationKline);

    //根据id 修改
    int updateApplicationKline(OtcKline applicationKline);

    OtcKline getApplicationLastKline(@Param("coinName") String coinName,
                                     @Param("legalName") String legalName,
                                     @Param("date") Date date);

    Page<OtcKline> getApplicationKline(@Param("coinName") String coinName,
                                       @Param("legalName") String legalName,
                                       RowBounds rowBounds);


}
