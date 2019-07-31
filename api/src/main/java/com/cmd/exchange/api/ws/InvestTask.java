package com.cmd.exchange.api.ws;

import com.cmd.exchange.common.vo.InvestDetailVO;
import com.cmd.exchange.service.InvestDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class InvestTask {
    private static Logger logger = LoggerFactory.getLogger(InvestTask.class);
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private InvestDetailService investDetailService;

    @Scheduled(fixedDelayString = "${cmd.invest.invest-time-limit}")
    public void sendInvestDetailCurrentList() {
        List<InvestDetailVO> investDetailVOList = investDetailService.getInvestDetailCurrentList();
        if(investDetailVOList.isEmpty()){
            return;
        }
        logger.info("=====最多查询100条最新投资，查询最新投资笔数====="+investDetailVOList.size());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(InvestDetailVO investDetailVO:investDetailVOList ){

            logger.info("时间"+ format.format(new Date())+"=====推送一条====="+investDetailVO);
            template.convertAndSend("/ws/market/invest-detail-current-list", investDetailVO);
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){

            }
        }
        //template.convertAndSend("/ws/market/invest-detail-current-list", investDetailVOList);
    }


}
