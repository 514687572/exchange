package com.cmd.exchange.task.timer;

import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.EthAddress;
import com.cmd.exchange.common.model.ReceivedCoin;
import com.cmd.exchange.service.CoinService;
import com.cmd.exchange.service.ConfigService;
import com.cmd.exchange.service.EthService;
import com.cmd.exchange.service.TransferService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将以太坊上各个地址的钱汇聚到一个目的地址
 */
@Component
public class GatherEthTask {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ConfigService configService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private EthService ethService;
    @Autowired
    private TransferService transferService;

    private Thread gatherThread;

    @Scheduled(cron = "0 0 4 * * ?")
    public synchronized void gatherEthTimer() {
        if (gatherThread != null && gatherThread.isAlive()) {
            log.error("gatherEthTimer ia alive, cannot start new work");
            return;
        }
        gatherThread = new Thread("gatherEthTimer") {
            public void run() {
                gatherEthThread();
            }
        };
        gatherThread.start();
    }

    public void gatherEthThread() {
        try {
            log.info("begin gatherEthTimer");
            gatherEth();
            log.info("end gatherEthTimer");
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
        List<Coin> ethCoins = coinService.getCoinsByCategory(CoinCategory.ETH);
        if (ethCoins.size() == 0) {
            log.info("coin eth not found");
            return;
        }
        for (Coin coin : ethCoins) {
            try {
                gatherOneEth(coin);
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }


    private void gatherOneEth(Coin coin) {
        String coinBaseAddress = coin.getCoinBase();
        if (coinBaseAddress == null || coinBaseAddress.trim().length() == 0) {
            log.info("eth coin base address not found");
            return;
        }
        log.info("begin gatherOneEth " + ReflectionToStringBuilder.toString(coin));
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

        HashSet<String> sendAddress = new HashSet<String>();
        // 验证地址，要去地址里面必须有钱，此方法最验证最准确
        Web3j web3 = Web3j.build(new HttpService("http://" + coin.getServerAddress() + ":" + coin.getServerPort()));
        if (checkAndGetBalance(web3, coinBaseAddress, new BigDecimal("0.1")).compareTo(BigDecimal.ZERO) == 0) {
            log.info("coinBaseAddress not valid.it must has 0.1 eth,coinBaseAddress=" + coinBaseAddress);
            return;
        }
        log.info("coinBaseAddress=" + coinBaseAddress);
        sendAddress.add(coinBaseAddress);

        // 获取所有以太坊地址，如果地址的币大于指定数量，那么收集起来
        int lastId = configService.getConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), 0);
        int beginLastId = lastId;
        int count = 0;
        log.info("begin analyze last id=" + lastId);
        BigDecimal totalEth = new BigDecimal("0");
        while (true) {
            ReceivedCoin receivedCoin = transferService.getNextReceiveFromExternal(lastId, coin.getName());
            if (receivedCoin == null) {
                break;
            }
            lastId = receivedCoin.getId();
            if (sendAddress.contains(receivedCoin.getAddress())) {
                configService.setConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), Integer.toString(lastId));
                log.warn("address already check. address:" + receivedCoin.getAddress());
                continue;
            }
            BigDecimal balance = checkAndGetBalance(web3, receivedCoin.getAddress(), gatherMinBalance);
            if (balance.equals(BigDecimal.ZERO)) {
                configService.setConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), Integer.toString(lastId));
                continue;
            }
            BigDecimal transfer = balance.subtract(gatherRemainBalance);
            log.info("begin transfer from " + receivedCoin.getAddress() + ",to " + coinBaseAddress + ",value=" + transfer);
            EthAddress ethAddress = ethService.getEthAddressByAddress(receivedCoin.getAddress());
            if (ethAddress == null) {
                log.warn("do not find eth address:" + receivedCoin.getAddress());
                configService.setConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), Integer.toString(lastId));
                continue;
            }
            //String hash = null;
            sendAddress.add(receivedCoin.getAddress());
            String hash = ethService.sendToAddress(ethAddress, coin.getName(), coinBaseAddress, transfer.doubleValue());
            log.info("end transfer from " + receivedCoin.getAddress() + ",to " + coinBaseAddress + ",value=" + transfer + ",hash=" + hash);
            totalEth = totalEth.add(transfer);
            count++;
            configService.setConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), Integer.toString(lastId));
            // 测试，休眠30秒避免造成过大压力
            try {
                Thread.sleep(1000 * 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("gather eth count=" + count + ",total eth=" + totalEth + ",beginLastId=" + beginLastId + ",now lastId=" + lastId);
    }
}
