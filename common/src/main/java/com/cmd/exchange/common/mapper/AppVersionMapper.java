package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.AppVersion;
import com.cmd.exchange.common.model.OtcPay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppVersionMapper {
    AppVersion getAppVersion(@Param("platform") String platform);
}
