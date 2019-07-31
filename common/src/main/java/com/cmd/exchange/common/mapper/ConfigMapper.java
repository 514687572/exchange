package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.Config;
import com.cmd.exchange.common.model.Config;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface ConfigMapper {

    @Options(useGeneratedKeys = true)
    @Insert("insert into t_config(conf_name,conf_value,comment)values(#{confName},#{confValue},#{comment})")
    int insertConfig(Config config);

    @Select("select * from t_config where conf_name=#{name}")
    Config getConfigByName(@Param("name") String name);

    /**
     * 获取记录，并且锁定该行记录
     * 这个语句必须在事务中，否则抛出异常，因为没有任何意义
     *
     * @param name 属性名称
     * @return
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Select("select * from t_config where conf_name=#{name} for update")
    Config getConfigByNameForUpdate(@Param("name") String name);

    @Update("update t_config set conf_value=#{value} where conf_name=#{name}")
    int updateConfigValue(@Param("name") String name, @Param("value") String value);

    @Select("select * from t_config")
    List<Config> getConfigList();
}
