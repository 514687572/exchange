package com.cmd.exchange.service;

import com.cmd.exchange.common.constants.MonitorTradeType;
import com.cmd.exchange.common.mapper.CashMonitoringConfigMapper;
import com.cmd.exchange.common.mapper.ReceivedCoinMapper;
import com.cmd.exchange.common.mapper.SendCoinMapper;
import com.cmd.exchange.common.model.CashMonitoringConfig;
import com.cmd.exchange.common.model.TimeMonitoring;
import com.cmd.exchange.common.vo.CashMonitoringVO;
import com.cmd.exchange.common.vo.CoinTransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CashMonitoringService {

    @Autowired
    private ReceivedCoinMapper receivedCoinMapper;

    @Autowired
    private SendCoinMapper sendCoinMapper;

    @Autowired
    private CashMonitoringConfigMapper cashMonitoringConfigMapper;

    @Autowired
    private TimeMonitoringService timeMonitoringService;

    private static List<CashMonitoringVO> transferList;

    private static long refreshTime = System.currentTimeMillis() / 1000;


    public void initGetMonitoringVOList(List<CashMonitoringConfig> configs) {
        Date date = new Date();
        refreshTime = date.getTime() / 1000;

        List<CashMonitoringVO> cashMonitoringVOList = new ArrayList<>();
        List<CoinTransferVo> reciveTransferList = new ArrayList<CoinTransferVo>();
        List<CoinTransferVo> sendTransferList = new ArrayList<CoinTransferVo>();
        //获取接收的币种
        for (CashMonitoringConfig data : configs) {
            List<CoinTransferVo> coinTransferVoList1 = new ArrayList<>();
            if (data.getRollInNumber() != null && new BigDecimal(data.getRollInNumber()).compareTo(new BigDecimal(0)) > 0) {
                coinTransferVoList1 = receivedCoinMapper.getReceivedCoinListAllByTimeAndNumber(data.getLastRefreshTime(), data.getCoinName(), new BigDecimal(data.getRollInNumber()));
                if (coinTransferVoList1 != null && coinTransferVoList1.size() > 0) {
                    reciveTransferList.addAll(coinTransferVoList1);
                    coinTransferVoList1 = new ArrayList<>();
                }
            }
            if (data.getRollOutNumber() != null && new BigDecimal(data.getRollOutNumber()).compareTo(new BigDecimal(0)) > 0) {
                coinTransferVoList1 = sendCoinMapper.getSendCoinListAllByTime(data.getCoinName(), data.getLastRefreshTime(), new BigDecimal(data.getRollOutNumber()));
                if (coinTransferVoList1 != null && coinTransferVoList1.size() > 0) {
                    sendTransferList.addAll(coinTransferVoList1);
                }
            }
        }
        //构造转入的数据
        if (reciveTransferList != null && reciveTransferList.size() > 0) {
            for (CoinTransferVo vo : reciveTransferList) {
                CashMonitoringVO cashMonitoringVO = new CashMonitoringVO();
                if (vo.getMobile() != "" && vo.getMobile() != null) {
                    cashMonitoringVO.setAccount(vo.getMobile());
                } else {
                    cashMonitoringVO.setAccount(vo.getEmail());
                }
                cashMonitoringVO.setType("1");
                cashMonitoringVO.setRollInNumber(vo.getAmount());
                cashMonitoringVO.setCoinName(vo.getCoinName());
                cashMonitoringVO.setRefreshTime(date);
                cashMonitoringVO.setGoalAddress(vo.getTargetAddress());
                Date timeDate = getDateForTimeStamp(vo.getReceivedTime());
                cashMonitoringVO.setCreateTime(timeDate);
                cashMonitoringVOList.add(cashMonitoringVO);
            }
        }
        //构造转出的数据
        if (sendTransferList != null && sendTransferList.size() > 0) {
            for (CoinTransferVo vo : sendTransferList) {
                CashMonitoringVO cashMonitoringVO = new CashMonitoringVO();
                if (vo.getMobile() != "" && vo.getMobile() != null) {
                    cashMonitoringVO.setAccount(vo.getMobile());
                } else {
                    cashMonitoringVO.setAccount(vo.getEmail());
                }
                cashMonitoringVO.setType("2");
                cashMonitoringVO.setRollOutNumber(vo.getAmount());
                cashMonitoringVO.setCoinName(vo.getCoinName());
                cashMonitoringVO.setRefreshTime(date);
                cashMonitoringVO.setGoalAddress(vo.getTargetAddress());
                cashMonitoringVO.setCreateTime(vo.getTransferTime());
                cashMonitoringVOList.add(cashMonitoringVO);
            }
        }
        if (cashMonitoringVOList != null && cashMonitoringVOList.size() > 1) {
            //将警告列表根据时间排序
            Collections.sort(cashMonitoringVOList, new Comparator<CashMonitoringVO>() {
                @Override
                public int compare(CashMonitoringVO o1, CashMonitoringVO o2) {

                    if (o1.getCreateTime().getTime() > o2.getCreateTime().getTime()) {
                        return -1;
                    }
                    if (o1.getCreateTime().getTime() == o2.getCreateTime().getTime()) {
                        return 0;
                    }
                    return 1;
                }
            });
        }
        transferList = cashMonitoringVOList;
        cashMonitoringConfigMapper.updateRefreshTimeById((int) (date.getTime() / 1000));

    }


    /**
     * 获取监控列表
     *
     * @param
     * @return
     */
    public List<CashMonitoringVO> getCashMonitorVoList(List<CashMonitoringConfig> cashMonitoringConfigs) {
        TimeMonitoring timeMonitoring = timeMonitoringService.getTimeMonitoringByType(MonitorTradeType.HIGH_HZ_TIME_MONITORING_COIN);
        Date date = new Date();
        System.out.println(date.getTime() / 1000 - refreshTime);
        if (date.getTime() / 1000 - refreshTime >= timeMonitoring.getNumMinutes() * 60) {
            System.out.println("当前刷新时间大于了设置的间隔时间" + refreshTime);
            initGetMonitoringVOList(cashMonitoringConfigs);
        }
        return transferList;
    }


    //时间搓转换成时间
    public Date getDateForTimeStamp(Integer times) {
        long msl = (long) times * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp = null;
        if (times != null) {
            try {
                String str = sdf.format(msl);
                temp = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }


}
