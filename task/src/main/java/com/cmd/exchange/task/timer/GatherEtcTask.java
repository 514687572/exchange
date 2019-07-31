package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.EthAddress;
import com.cmd.exchange.service.CoinService;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.EtcService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.List;

/**
 * 将以太坊上各个地址的钱汇聚到一个目的地址
 */
@Component
public class GatherEtcTask {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ConfigService configService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private EtcService etcService;

    private Thread gatherThread;

    //@Scheduled(cron = "0 0 0 * * ?")
    public synchronized void gatherEtcTimer() {
        if (gatherThread != null && gatherThread.isAlive()) {
            log.error("gatherEthTimer ia alive, cannot start new work");
            return;
        }
        gatherThread = new Thread("gatherEtcThread") {
            public void run() {
                gatherEtcThread();
            }
        };
        gatherThread.start();
    }

    public void gatherEtcThread() {
        try {
            log.info("begin gatherEtcTimer");
            gatherEth();
            log.info("end gatherEtcTimer");
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    /**
     * 检查资产是否足够，不够的时候直接返回0
     *
     * @param web3
     * @param address
     * @param ignoreBalance 可用余额小于这个金额将会忽略，直接返回0
     * @return 余额大于指定金额将会返回余额， 否则返回0
     */
    private BigDecimal checkAndGetBalance(Web3j web3, String address, BigDecimal ignoreBalance) {
        try {
            EthGetBalance getBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            BigDecimal balance = Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
            if (balance.compareTo(ignoreBalance) >= 0) {
                return balance;
            }
        } catch (Exception ex) {
            log.warn("", ex);
        }
        return BigDecimal.ZERO;
    }


    private void gatherEth() {
        // 获取汇聚的地址
        List<Coin> ethCoins = coinService.getCoinsByCategory(CoinCategory.ETC);
        if (ethCoins.size() == 0) {
            log.info("coin eth not found");
            return;
        }
        Coin coin = ethCoins.get(0);
        String coinBaseAddress = coin.getCoinBase();
        if (coinBaseAddress == null || coinBaseAddress.trim().length() == 0) {
            log.info("eth coin base address not found");
            return;
        }
        coinBaseAddress = coinBaseAddress.trim();
        // 获取用户余额超过某个金额的时候进行回收
        String gatherMinBalanceStr = configService.getConfigValue(ConfigKey.GATHER_MIN_BALANCE_PRE + coin.getName(), "0.5");
        BigDecimal gatherMinBalance = new BigDecimal(gatherMinBalanceStr);
        log.info("gatherMinBalance=" + gatherMinBalance);
        // 获取用户余额需要保留的余额
        String gatherRemainStr = configService.getConfigValue(ConfigKey.GATHER_REMAIN_PRE + coin.getName(), "0.01");
        BigDecimal gatherRemainBalance = new BigDecimal(gatherRemainStr);
        log.info("gatherRemainBalance=" + gatherRemainBalance);
        if (gatherMinBalance.compareTo(gatherRemainBalance) <= 0) {
            log.info("gatherMinBalance <= gatherRemainBalance, can not work");
            return;
        }

        // 验证地址，要去地址里面必须有钱，此方法最验证最准确
        Web3j web3 = Web3j.build(new HttpService("http://" + coin.getServerAddress() + ":" + coin.getServerPort()));
        if (checkAndGetBalance(web3, coinBaseAddress, new BigDecimal("0.1")).compareTo(BigDecimal.ZERO) == 0) {
            log.info("coinBaseAddress not valid.it must has 0.1 eth,coinBaseAddress=" + coinBaseAddress);
            return;
        }

        // 获取所有以太坊地址，如果地址的币大于指定数量，那么收集起来
        int lastId = 0;
        int count = 0;
        BigDecimal totalEtc = new BigDecimal("0");
        while (true) {
            EthAddress etcAddress = etcService.getNextAddress(lastId);
            if (etcAddress == null) {
                break;
            }
            BigDecimal balance = checkAndGetBalance(web3, etcAddress.getAddress(), gatherMinBalance);
            if (balance.equals(BigDecimal.ZERO)) {
                continue;
            }
            BigDecimal transfer = balance.subtract(gatherRemainBalance);
            String hash = etcService.sendToAddress(etcAddress, coin.getName(), coinBaseAddress, transfer.doubleValue());
            log.info("transfer from " + etcAddress.getAddress() + ",to" + coinBaseAddress + ",value=" + transfer + ",hash=" + hash);
            totalEtc = totalEtc.add(transfer);
            count++;
            lastId = etcAddress.getId();
        }
        log.info("gather etc count=" + count + ",total etc=" + totalEtc);
    }
}
