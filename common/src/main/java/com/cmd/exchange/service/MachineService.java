package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.MachineStatus;
import com.cmd.exchange.common.constants.MachineType;
import com.cmd.exchange.common.enums.ValueEnum;
import com.cmd.exchange.common.mapper.MachineMapper;
import com.cmd.exchange.common.model.Machine;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.MachineVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 矿机 业务
 */
@Service
public class MachineService {

    @Autowired
    private MachineMapper machineMapper;

    public int addMachine(Machine machine) {
        Assert.check(machine == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(
                machine.getMachineCoin() == null ||
                        machine.getMachineName() == null ||
                        machine.getMachineGive() == null ||
                        machine.getMachineType() == null ||
                        machine.getStatus() == null, ErrorCode.ERR_PARAM_ERROR);
        ValueEnum.valueOfEnum(MachineType.values(), machine.getMachineType());
        ValueEnum.valueOfEnum(MachineStatus.values(), machine.getStatus());


        Machine oneInfo = machineMapper.getOneInfo(new Machine().setMachineName(machine.getMachineName()));
        Assert.check(oneInfo != null, ErrorCode.ERR_RECORD_EXIST);

        return machineMapper.add(machine);
    }

    public int updateMachine(Machine machine) {
        Assert.check(machine == null, ErrorCode.ERR_PARAM_ERROR);
        Assert.check(
                machine.getMachineCoin() == null ||
                        machine.getMachineName() == null ||
                        machine.getMachineGive() == null ||
                        machine.getMachineType() == null ||
                        machine.getStatus() == null ||
                        machine.getId() == null, ErrorCode.ERR_PARAM_ERROR);
        ValueEnum.valueOfEnum(MachineType.values(), machine.getMachineType());
        ValueEnum.valueOfEnum(MachineStatus.values(), machine.getStatus());
        return machineMapper.mod(machine);
    }

    public List<MachineVo> getCoinConfigList(MachineVo machineVo) {

        Machine machine = new Machine();

        BeanUtils.copyProperties(machineVo, machine);

        List<Machine> infos = machineMapper.getInfo(machine, new RowBounds(machineVo.getPageNo(), machineVo.getPageSize()));

        return getMachineVos(infos);
    }


    private List<MachineVo> getMachineVos(List<Machine> infos) {
        if (null == infos) return new ArrayList<>();

        List<MachineVo> machineVos = new ArrayList<>();

        for (Machine machine1 : infos) {

            MachineVo machineVo1 = new MachineVo();

            BeanUtils.copyProperties(machine1, machineVo1);

            machineVos.add(machineVo1);
        }

        return machineVos;
    }
}
