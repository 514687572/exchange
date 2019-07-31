package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.MachineStatus;
import com.cmd.exchange.common.constants.MachineType;
import com.cmd.exchange.common.enums.ValueEnum;
import com.cmd.exchange.common.mapper.MachineMapper;
import com.cmd.exchange.common.mapper.UserMachineMapper;
import com.cmd.exchange.common.model.Machine;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserMachine;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.vo.MachineVo;
import com.cmd.exchange.common.vo.UserMachineVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户矿机 业务
 */
@Service
public class UserMachineService {
    private Logger log = LoggerFactory.getLogger(UserMachineService.class);
    @Autowired
    private UserMachineMapper userMachineMapper;
    @Autowired
    private MachineMapper machineMapper;

    public int addMachine(UserMachine userMachine) {
        Assert.check(userMachine == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(
                userMachine.getMachineCoin() == null ||
                        userMachine.getMachineName() == null ||
                        userMachine.getMachineId() == null ||
                        userMachine.getUserId() == null ||
                        userMachine.getMachineStatus() == null

                , ErrorCode.ERR_PARAM_ERROR);

        ValueEnum.valueOfEnum(MachineStatus.values(), userMachine.getMachineStatus());
        return userMachineMapper.add(userMachine);
    }
    public boolean userAddMachine(int userId,int machineType){

        Machine oneInfo = machineMapper.getOneInfo(new Machine().setMachineType(machineType));

        if(null == oneInfo || oneInfo.getStatus() == MachineStatus.DISABLE.getValue()){
            log.info("not userAddMachine 0");
            return false;
        }
        UserMachine userMachine = new UserMachine();
        userMachine.setMachineStatus(oneInfo.getStatus());
        userMachine.setMachineCoin(oneInfo.getMachineCoin());
        userMachine.setMachineId(oneInfo.getId());
        userMachine.setMachineType(oneInfo.getMachineType());
        userMachine.setUserId(userId);
        userMachine.setMachineName(oneInfo.getMachineName());
        userMachine.setYesterdayTime(oneInfo.getAddTime());
        userMachine.setIsOpen(MachineStatus.DISABLE.getValue());
        addMachine(userMachine);
        log.info("create userAddMachine success ");

        return true;
    }
    public int updateMachine(UserMachine userMachine) {
        Assert.check(userMachine == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userMachine.getId() == null, ErrorCode.ERR_PARAM_ERROR);

        return userMachineMapper.mod(userMachine);
    }
    public int machineOpem(UserMachine userMachine) {
        Assert.check(userMachine == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userMachine.getUserId() == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(userMachine.getIsOpen() == null, ErrorCode.ERR_PARAM_ERROR);
        MachineStatus.fromInt(userMachine.getIsOpen());
        int i = userMachineMapper.machineOpem(userMachine);
        Assert.check(i<1,ErrorCode.ERR_RECORD_NOT_EXIST);

        return i;
    }

    public List<UserMachineVo> getUserMachineList(UserMachineVo userMachineVo) {

        UserMachine machine = new UserMachine();

        BeanUtils.copyProperties(userMachineVo, machine);

        List<UserMachine> infos = null; //userMachineMapper.getInfo(machine, new RowBounds(userMachineVo.getPageNo(), userMachineVo.getPageSize()));

        return getUserMachineVos(infos);
    }


    private List<UserMachineVo> getUserMachineVos(List<UserMachine> infos) {
        if (null == infos) return new ArrayList<>();

        List<UserMachineVo> machineVos = new ArrayList<>();

        for (UserMachine machine1 : infos) {

            UserMachineVo machineVo1 = new UserMachineVo();
            Machine oneInfo = machineMapper.getOneInfo(new Machine().setId(machine1.getMachineId()));

            BeanUtils.copyProperties(machine1, machineVo1);
            machineVo1.setMachineCoin(oneInfo.getMachineCoin());
            machineVo1.setMachineName(oneInfo.getMachineName());
           // machineVo1.setYesterdayIncome(oneInfo.getMachineGive());
            machineVos.add(machineVo1);
        }

        return machineVos;
    }

    public List<UserMachineVo> getUserMachines(UserMachineVo userMachineVo) {
        UserMachine machine = new UserMachine();

        BeanUtils.copyProperties(userMachineVo, machine);

        List<UserMachine> infos = userMachineMapper.getInfo(machine);

        return getUserMachineVos(infos);
    }

    public List<UserMachine> yesterdayUserMachineProfitList(){
        //计算今天凌晨
        int dayBeginTimestamp = DateUtil.getDayBeginTimestamp(System.currentTimeMillis());
        Date endLastTime = new Date(1000L * dayBeginTimestamp);
        //计算昨天
        int yesterdayBeginTimestamp = dayBeginTimestamp - 3600 * 24;
        Date startLastTime = new Date(1000L * yesterdayBeginTimestamp);
        Date todayBegin = new Date(1000L * dayBeginTimestamp);
        UserMachine userMachine = new UserMachine();
        userMachine.setStartLastTime(startLastTime);
        userMachine.setEndLastTime(todayBegin);
        userMachine.setIsOpen(MachineStatus.NORMAL.getValue());

        return userMachineMapper.getInfo(userMachine);
    }
}
