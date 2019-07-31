package com.cmd.exchange.admin.service;

import com.cmd.exchange.admin.componet.excelbean.PatchSenCoinBean;
import com.cmd.exchange.admin.excel.ClassCasrUtil;
import com.cmd.exchange.admin.excel.ExcelReader;
import com.cmd.exchange.admin.vo.PatchErrorVO;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.DispatchReqVO;
import com.cmd.exchange.common.vo.DispatchVO;
import com.cmd.exchange.service.DispatchService;
import com.cmd.exchange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExcelPatchCoinService {
    @Autowired
    private UserService userService;

    @Autowired
    private DispatchService dispatchService;

    public List<PatchErrorVO> patchService(String filePath, String coinName, Integer dispatchId) {
        List<PatchErrorVO> patchErrorVOS = new ArrayList<>();
        ExcelReader eh = new ExcelReader(filePath, "Sheet1");
        log.info("cell number is {}", ExcelReader.cellNumber);
        log.info("row number is {}", ExcelReader.rowNumber);
        List<String> titles = eh.getTitleList(eh, ExcelReader.cellNumber);
        List<Object> userList = eh.getDataList(PatchSenCoinBean.class, eh, ExcelReader.cellNumber, +ExcelReader.rowNumber + 1, titles);
        List<PatchSenCoinBean> list = new ArrayList<>();
        for (Object object : userList) {
            PatchSenCoinBean patchSenCoinBean = ClassCasrUtil.get(PatchSenCoinBean.class, object);
            list.add(patchSenCoinBean);
        }

        //检测用户是否存在
        for (PatchSenCoinBean data : list) {
            User user = userService.getUserByEmail(data.getUserAcount());
            if (user == null) {
                user = userService.getUserByMobile(data.getUserAcount());
                if (user == null) {
                    PatchErrorVO patchErrorVO = new PatchErrorVO();
                    patchErrorVO.setRemark("用户：" + data.getUserAcount() + "不存在");
                    patchErrorVO.setUserAccount(data.getUserAcount());
                    patchErrorVOS.add(patchErrorVO);
                }
            }
        }

        //检测配置标配置的数量对不对
        for (PatchSenCoinBean data : list) {
            try {
                BigDecimal sendCoinNumber = new BigDecimal(data.getSendCoinNumber());

            } catch (Exception e) {
                PatchErrorVO patchErrorVO = new PatchErrorVO();
                patchErrorVO.setRemark("用户：" + data.getUserAcount() + "设置的数量有误");
                patchErrorVO.setUserAccount(data.getUserAcount());
                patchErrorVOS.add(patchErrorVO);
            }
        }


        if (patchErrorVOS != null && patchErrorVOS.size() > 0) {
            return patchErrorVOS;
        }
        DispatchReqVO dispatchReqVO = new DispatchReqVO();
        dispatchReqVO.setDispatchId(dispatchId);
        dispatchReqVO.setComment("excel批量拨币");
        List<DispatchVO> dispatchVOList = new ArrayList<>();
        for (PatchSenCoinBean data : list) {
            User user = userService.getUserByEmail(data.getUserAcount());
            DispatchVO dispatchVO = new DispatchVO();
            dispatchVO.setCoinName(coinName);
            dispatchVO.setAmount(new BigDecimal(data.getSendCoinNumber()));
            if (user == null) {
                user = userService.getUserByMobile(data.getUserAcount());

            }
            dispatchVO.setUserId(user.getId());
            dispatchVO.setMobile(user.getMobile());
            dispatchVOList.add(dispatchVO);
        }
        dispatchReqVO.setList(dispatchVOList);
        //开始拨币
        if (dispatchReqVO != null && dispatchReqVO.getList() != null && dispatchReqVO.getList().size() > 0) {
            dispatchService.dispatch(dispatchReqVO);
        }

        return patchErrorVOS;
    }
}
