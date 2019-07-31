package com.cmd.exchange.common.mapper;

import com.cmd.exchange.common.model.ServerConfig;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ServerConfigMapper {

    int addServerConfig(ServerConfig serverConfig);

    int delServerConfigById(@Param("id") Integer id);

    int updateServerConfig(ServerConfig serverConfig);

    ServerConfig getServerConfigById(@Param("id") Integer id);

    List<ServerConfig> serverConfigList();

    Page<ServerConfig> getServerConfigPage(RowBounds rowBounds);


}
