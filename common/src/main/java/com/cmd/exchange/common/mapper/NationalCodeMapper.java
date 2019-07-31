package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.NationalCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NationalCodeMapper {

    @Select("select * from t_national_code")
    List<NationalCode> getNationalCode();
}
