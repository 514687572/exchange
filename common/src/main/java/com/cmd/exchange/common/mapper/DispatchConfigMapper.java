package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.DispatchConfig;
import com.cmd.exchange.common.model.DispatchJob;
import com.cmd.exchange.common.vo.DispatchConfigVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DispatchConfigMapper {
    int add(DispatchConfig dispatchConfig);

    int adminAdd(DispatchConfigVo dispatchConfigVo);

    int mod(DispatchConfig dispatchConfig);

    int updateDispatchConfig(DispatchConfigVo dispatchConfigVo);

    int del(@Param("id") Integer id);

    DispatchConfigVo getDispatchConfigInfoById(@Param("id") Integer id);

    DispatchConfig getDispatchConfigById(@Param("id") Integer id);

    List<DispatchConfigVo> getDispatchConfig();

}
