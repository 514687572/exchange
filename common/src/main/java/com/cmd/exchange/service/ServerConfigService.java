package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ServerType;
import com.cmd.exchange.common.mapper.ServerConfigMapper;
import com.cmd.exchange.common.model.ServerConfig;
import com.cmd.exchange.common.vo.ServiceMontitorVO;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ServerConfigService {
    @Autowired
    private ServerConfigMapper serverConfigMapper;

    @Autowired
    private RedisTemplate<String, String> redTemplate;

    public int addServerConfig(ServerConfig serverConfig) {
        return serverConfigMapper.addServerConfig(serverConfig);
    }

    public int delServerConfigById(Integer id) {
        return serverConfigMapper.delServerConfigById(id);
    }

    public int updateServerConfig(ServerConfig serverConfig) {
        return serverConfigMapper.updateServerConfig(serverConfig);
    }

    public List<ServerConfig> serverConfigList() {
        return serverConfigMapper.serverConfigList();
    }

    public Page<ServerConfig> getServerConfigPage(Integer pageNo, Integer pageSize) {
        return serverConfigMapper.getServerConfigPage(new RowBounds(pageNo, pageSize));
    }

    public ServerConfig getServicceConfigById(Integer id) {
        return serverConfigMapper.getServerConfigById(id);
    }

    public List<ServiceMontitorVO> getServiceMototorList() {
        List<ServiceMontitorVO> list = new ArrayList<ServiceMontitorVO>();
        boolean redisBool = true;
        ServiceMontitorVO redisVo = new ServiceMontitorVO();

        List<ServerConfig> serverConfigList = serverConfigList();
        if (serverConfigList != null && serverConfigList.size() > 0) {
            for (ServerConfig data : serverConfigList) {

                String redisKey = null;
                if (data.getServerType().equals(ServerType.API_SERVER)) {
                    redisKey = ServerType.API_SERVER + data.getServerInnerAddress();
                }
                if (data.getServerType().equals(ServerType.TASK_SERVER)) {
                    redisKey = ServerType.TASK_SERVER + data.getServerInnerAddress();
                }
                if (redisKey != null) {
                    ServiceMontitorVO severVO = new ServiceMontitorVO();
                    severVO.setServiceName(data.getServerName());
                    severVO.setServiceStatus(false);
                    severVO.setInternalIp(data.getServerInnerAddress());
                    severVO.setExternalIp(data.getServerExternalAddress());
                    severVO.setExternalPort(data.getPort());
                    String value = this.redTemplate.opsForValue().get(redisKey);
                    try {
                        if (value != null && value.equals("OK")) {
                            severVO.setServiceStatus(true);
                        }
                    } catch (Exception e) {
                        redisBool = false;
                        redisVo.setExceptionContext(e.getMessage());
                        log.debug("redis Exception", e);
                    }
                    list.add(severVO);
                } else {
                    if (!data.getServerType().equals(ServerType.DB_SERVER)) {
                        ServiceMontitorVO otherSever = new ServiceMontitorVO();
                        otherSever.setServiceName(data.getServerName());
                        otherSever.setServiceStatus(true);
                        otherSever.setInternalIp(data.getServerInnerAddress());
                        otherSever.setExternalIp(data.getServerExternalAddress());
                        otherSever.setExternalPort(data.getPort());
                        list.add(otherSever);
                    } else {
                        redisVo.setServiceName(data.getServerName());
                        redisVo.setServiceStatus(true);
                        redisVo.setInternalIp(data.getServerInnerAddress());
                        redisVo.setExternalIp(data.getServerExternalAddress());
                        redisVo.setExternalPort(data.getPort());
                    }
                }
            }
        }
        redisVo.setServiceStatus(redisBool);
        list.add(redisVo);
        return list;
    }

}
