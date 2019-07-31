package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.constants.UserBillReason;
import com.cmd.exchange.common.enums.ApplicationOrderStatus;
import com.cmd.exchange.common.enums.ApplicationType;
import com.cmd.exchange.common.mapper.*;
import com.cmd.exchange.common.model.Otc;
import com.cmd.exchange.common.model.OtcBill;
import com.cmd.exchange.common.model.OtcMarket;
import com.cmd.exchange.common.model.OtcOrder;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.C2cApplicationVO;
import com.cmd.exchange.common.vo.C2cOrderDetailVO;
import com.cmd.exchange.common.vo.C2cOrderVO;
import com.cmd.exchange.common.vo.ChatLogVo;
import com.cmd.exchange.service.UserCoinService;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class C2cService {

    @Autowired
    C2cDao c2cDao;
    @Autowired
    OtcOrderMapper otcOrderMapper;
    @Autowired
    ChatMapper chatMapper;

    /**
     * @Desc: 查看聊天记录
     */
    public Page<ChatLogVo> getChatLogByOrderId(Long id, Integer pageNo, Integer pageSize) {
        OtcOrder order = otcOrderMapper.getApplicationOrderById(id);
        Assert.check(order == null, ErrorCode.ERR_C2C_ORDER_NOT_EXIST);
        Page<ChatLogVo> chatLogVos = chatMapper.getChatLogByOrder(
                order.getWithdrawalUserId(),
                order.getDepositUserId(),
                order.getCreateTime(),
                order.getFinishTime(),
                new RowBounds(pageNo, pageSize));
        return chatLogVos;
    }

    /**
     * @Desc: 查看订单详情
     */
    public C2cOrderDetailVO getOrderDetail(Long id) {
        return c2cDao.getC2cOrderDetail(id);
    }

    /**
     * @Desc: 获取订单列表
     */
    public Page<C2cOrderVO> getOrders(String buyName, String sellName, String coinName,
                                      Integer status, String startTime, String endTime, Integer pageNo, Integer pageSize) {

        C2cOrderVO c2cOrderVO = formatOrder(buyName, sellName, coinName, status, startTime, endTime);

        return c2cDao.getC2cOrders(c2cOrderVO, new RowBounds(pageNo, pageSize));
    }

    /**
     * @Desc: 导出订单
     */
    public List<C2cOrderVO> downloadOrders(String buyName, String sellName, String coinName,
                                           Integer status, Date begin, Date end) {

        C2cOrderVO c2cOrderVO = formatOrder(buyName, sellName, coinName, status, null, null);

        return c2cDao.downloadOrders(c2cOrderVO, begin, end);
    }

    /**
     * @Desc: 获取挂单列表
     */
    public Page<C2cApplicationVO> getApplications(Integer type, Integer status, String coinName, String orderNo,
                                                  String user, String startTime, String endTime, Integer pageNo, Integer pageSize) {

        C2cApplicationVO c2cApplicationVO = formatApplication(type, status, coinName, orderNo, user, startTime, endTime);

        return c2cDao.getC2cApplications(c2cApplicationVO, new RowBounds(pageNo, pageSize));
    }

    /**
     * @Desc: 导出挂单
     */

    public List<C2cApplicationVO> downloadApplications(Integer type, Integer status, String coinName, String orderNo,
                                                       String user, Date begin, Date end) {

        C2cApplicationVO c2cApplicationVO = formatApplication(type, status, coinName, orderNo, user, null, null);

        return c2cDao.downloadApplications(c2cApplicationVO, begin, end);
    }


    private C2cOrderVO formatOrder(String buyName, String sellName, String coinName, Integer status, String startTime, String endTime) {

        C2cOrderVO c2cOrderVO = new C2cOrderVO();
        c2cOrderVO.setStatus(status);

        if (coinName != null && !coinName.equals("")) {
            c2cOrderVO.setCoinName(coinName);
        }
        if (buyName != null && !buyName.equals("")) {
            c2cOrderVO.setBuyUser(buyName);
        }
        if (sellName != null && !sellName.equals("")) {
            c2cOrderVO.setSellUser(sellName);
        }
        if (startTime != null && !startTime.equals("")) {
            c2cOrderVO.setStartTime(startTime);
        }
        if (endTime != null && !endTime.equals("")) {
            c2cOrderVO.setEndTime(endTime);
        }
        return c2cOrderVO;
    }

    private C2cApplicationVO formatApplication(Integer type, Integer status, String coinName, String orderNo, String user, String startTime, String endTime) {

        C2cApplicationVO c2cApplicationVO = new C2cApplicationVO();
        c2cApplicationVO.setType(type).setStatus(status);
        if (coinName != null && !coinName.equals("")) {
            c2cApplicationVO.setCoinName(coinName);
        }
        if (orderNo != null && !orderNo.equals("")) {
            c2cApplicationVO.setOrderNo(orderNo);
        }
        if (user != null && !user.equals("")) {
            c2cApplicationVO.setCreateUser(user);
        }

        if (startTime != null && !startTime.equals("")) {
            c2cApplicationVO.setStartTime(startTime);
        }
        if (endTime != null && !endTime.equals("")) {
            c2cApplicationVO.setEndTime(endTime);
        }
        return c2cApplicationVO;
    }

}
