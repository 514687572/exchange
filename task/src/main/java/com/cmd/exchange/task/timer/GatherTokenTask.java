package com.cmd.exchange.task.timer;

import com.cmd.exchange.blockchain.eth.ETHCoin;
import com.cmd.exchange.blockchain.eth.EthUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.utils.Convert;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;

/**
 * 将以太坊代币上各个地址的钱汇聚到一个目的地址
 */
@Component
public class GatherTokenTask {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ConfigService configService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private EthService ethService;
    @Autowired
    private TransferService transferService;
    // 是否启用手机代币
    @Value("${task.gather_token.enable:true}")
    private boolean enable = true;

    private Thread gatherThread;

    @PostConstruct
    public void init() {
        log.info("init GatherTokenTask,enable=" + enable);
    }

    @Scheduled(cron = "0 30 4 * * ?")
    //@Scheduled(cron = "0/1 * * * * ?")
    public synchronized void gatherEthTimer() {
        if (!enable) {
            log.info("not enable token gather");
            return;
        }
        if (gatherThread != null && gatherThread.isAlive()) {
            log.error("gatherTokenTimer ia alive, cannot start new work");
            return;
        }
        gatherThread = new Thread("gatherTokenTimer") {
            public void run() {
                gatherEthThread();
            }
        };
        gatherThread.start();
    }

    public void gatherEthThread() {
        try {
            log.info("begin gatherTokenTimer");
            gatherEthToken();
            log.info("end gatherTokenTimer");
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    /**
     * 检查资产是否足够，不够的时候直接返回0
     *
     * @param coin
     * @param web3
     * @param address
     * @param ignoreBalance 可用余额小于这个金额将会忽略，直接返回0
     * @return 余额大于指定金额将会返回余额， 否则返回0
     */
    private BigDecimal checkAndGetBalance(Coin coin, Web3j web3, String address, BigDecimal ignoreBalance) {
        try {
            // 查询余额是不需要提供私钥的，这里随便写一个
            Credentials credentials = EthUtils.loadCredentialsByContect("1249418731", "{\"address\":\"17dfaad634c4521fa2d9ec3cc4112451a0235857\",\"id\":\"2eb5dca6-9028-4f7c-bf99-00c7a39db55f\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"006a62779fe48bac92b78fe99ae2ed2705a5e96f56f6ed692c38528802ba166c\",\"cipherparams\":{\"iv\":\"55fc586576f5d800e478e2a2d7cbf50a\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"da18243bde9086192105a85dcb56504ee6b576b0920e01d9aad64eb7117d366e\"},\"mac\":\"761a0804a326b457564e8a833e30d3484335ad58d2ec2906a6cdfa492b083c85\"}}");
            ETHCoin ethCoin = ETHCoin.load(coin.getContractAddress(),
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    Contract.GAS_PRICE,
                    Contract.GAS_LIMIT);
            BigInteger biBalance = ethCoin.balanceOf(address).send();

            BigDecimal balance = new BigDecimal(biBalance).divide(new BigDecimal("10").pow(coin.getDecimals()), 8, RoundingMode.HALF_UP);
            if (balance.compareTo(ignoreBalance) >= 0) {
                return balance;
            }
        } catch (Exception ex) {
            log.warn("", ex);
        }
        return BigDecimal.ZERO;
    }

    // 如果指定地址eth余额大于指定的数字，返回true
    private boolean checkEthBalance(Web3j web3, String address, BigDecimal checkBalance) throws Exception {
        EthGetBalance getBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigDecimal balance = Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
        if (balance.compareTo(checkBalance) >= 0) {
            return true;
        }
        return false;
    }

    private void gatherEthToken() {
        // 获取汇聚的地址
        List<Coin> tokenCoins = coinService.getCoinsByCategory(CoinCategory.TOKEN);
        if (tokenCoins.size() == 0) {
            log.info("coin eth not found");
            return;
        }
        for (Coin coin : tokenCoins) {
            try {
                gatherOneEthToken(coin);
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    private Coin getLinkEthCoin(Coin coin) {
        List<Coin> coins = coinService.getCoinsByCategory(CoinCategory.ETH);
        for (Coin ethCoin : coins) {
            if (ethCoin.getServerAddress().equalsIgnoreCase(coin.getServerAddress()) && ethCoin.getServerPort().equals(coin.getServerPort())) {
                return ethCoin;
            }
        }
        return null;
    }

    private void gatherOneEthToken(Coin coin) throws Exception {
        String coinBaseAddress = coin.getCoinBase();
        if (coinBaseAddress == null || coinBaseAddress.trim().length() == 0) {
            log.info("eth token coin base address not found");
            return;
        }
        log.info("begin gatherOneEthToken " + ReflectionToStringBuilder.toString(coin));
        ethService.updateEthDecimal(coin.getServerAddress(), coin.getServerPort(), coin);
        coinBaseAddress = coinBaseAddress.trim();
        // 获取用户余额超过某个金额的时候进行回收
        String gatherMinBalanceStr = configService.getConfigValue(ConfigKey.GATHER_MIN_BALANCE_PRE + coin.getName(), "5");
        BigDecimal gatherMinBalance = new BigDecimal(gatherMinBalanceStr);
        log.info("gatherMinBalance=" + gatherMinBalance);
        // 获取用户余额需要传的手续费
        BigDecimal gatherFee = configService.getConfigValue(ConfigKey.GATHER_TRANSFER_PRE_PRE + coin.getName(), BigDecimal.ZERO, false);
        log.info("gatherFee=" + gatherFee);

        HashSet<String> sendAddress = new HashSet<String>();
        // 验证地址，要去地址里面必须有钱，此方法最验证最准确
        Web3j web3 = Web3j.build(new HttpService("http://" + coin.getServerAddress() + ":" + coin.getServerPort()));
        if (checkAndGetBalance(coin, web3, coinBaseAddress, new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) == 0) {
            log.info("coinBaseAddress not valid.it must has 0.01 eth,coinBaseAddress=" + coinBaseAddress);
            return;
        }
        log.info("coinBaseAddress=" + coinBaseAddress);
        sendAddress.add(coinBaseAddress);

        // 如果配置了费用，给所有地址转一笔费用
        int lastId = configService.getConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), 0);
        int beginLastId = lastId;
        BigInteger gasPrice = null;
        if (gatherFee.compareTo(BigDecimal.ZERO) > 0) {
            log.info("begin transfer fee to address, last id=" + lastId);
            Coin ethCoin = getLinkEthCoin(coin);
            if (ethCoin == null) {
                log.error("can not get eth coin of token coin " + coin.getName());
                return;
            }
            log.info("get link eth coin:" + ReflectionToStringBuilder.toString(ethCoin));
            while (true) {
                ReceivedCoin receivedCoin = transferService.getNextReceiveFromExternal(lastId, coin.getName());
                if (receivedCoin == null) {
                    break;
                }
                lastId = receivedCoin.getId();
                if (sendAddress.contains(receivedCoin.getAddress())) {
                    log.warn("address already check. address:" + receivedCoin.getAddress());
                    continue;
                }
                BigDecimal balance = checkAndGetBalance(coin, web3, receivedCoin.getAddress(), gatherMinBalance);
                if (balance.equals(BigDecimal.ZERO)) {
                    continue;
                }
                // 判断币里面的以太币钱是否小于指定的费用，小于的话就转一笔
                if (!checkEthBalance(web3, receivedCoin.getAddress(), gatherFee)) {
                    log.info("begin transfer fee from " + ethCoin.getName() + ",to " + coinBaseAddress + ",value=" + gatherFee);
                    ethService.sendToAddress(null, ethCoin.getName(), receivedCoin.getAddress(), gatherFee.doubleValue());
                    log.info("end transfer fee from " + ethCoin.getName() + ",to " + coinBaseAddress + ",value=" + gatherFee);
                    Thread.sleep(1000 * 30);
                }
                sendAddress.add(receivedCoin.getAddress());
            }
        } else {
            gasPrice = BigInteger.ZERO;
        }

        // 获取所有以太坊地址，如果地址的币大于指定数量，那么收集起来
        sendAddress.clear();
        sendAddress.add(coinBaseAddress);
        lastId = beginLastId;
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
            BigDecimal balance = checkAndGetBalance(coin, web3, receivedCoin.getAddress(), gatherMinBalance);
            if (balance.equals(BigDecimal.ZERO)) {
                configService.setConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), Integer.toString(lastId));
                continue;
            }
            BigDecimal transfer = balance;
            log.info("begin transfer from " + receivedCoin.getAddress() + ",to " + coinBaseAddress + ",value=" + transfer);
            EthAddress ethAddress = ethService.getEthAddressByAddress(receivedCoin.getAddress());
            if (ethAddress == null) {
                log.warn("do not find eth address:" + receivedCoin.getAddress());
                configService.setConfigValue(ConfigKey.GATHER_LAST_RECV_ID_PRE + coin.getName(), Integer.toString(lastId));
                continue;
            }
            //String hash = null;
            sendAddress.add(receivedCoin.getAddress());
            String hash = ethService.sendToAddress(ethAddress, coin.getName(), coinBaseAddress, transfer.doubleValue(), gasPrice);
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
        log.info("gather " + coin.getName() + " count=" + count + ",total eth=" + totalEth + ",beginLastId=" + beginLastId + ",now lastId=" + lastId);
    }
}
