package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.OtcStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OtcStatMapper {
    int add(OtcStat otcStat);

    int mod(OtcStat otcStat);

    OtcStat getOtcStat(@Param("userId") Integer userId, @Param("coinName") String coinName);

    OtcStat statisticsTradeByUserId(@Param("userId") Integer userId, @Param("coinName") String coinName);

}
