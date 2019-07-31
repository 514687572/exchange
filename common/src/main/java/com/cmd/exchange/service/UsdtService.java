package com.cmd.exchange.service;

import com.cmd.exchange.blockchain.bitcoin.BitcoinJSONRPCClient;
import com.cmd.exchange.blockchain.bitcoin.BitcoindRpcClient;
import com.cmd.exchange.blockchain.eth.ETHHelper;
import com.cmd.exchange.blockchain.usdt.OmniClient;
import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.model.UserCoin;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.vo.UserCoinVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * 用于对比特币以及比特币山寨币系列的币种进行一些操作
 */
@Service
public class UsdtService extends CoinServiceBase {
    private static Log log = LogFactory.getLog(UsdtService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private SmsService smsService;
    // 交易确认的区块数
    private int txConfirmCount = 3;

    /**
     * 同步所有币的记录
     */
    public void syncNewTransactions() {
        List<Coin> coins = coinService.getCoinsByCategory(CoinCategory.USDT);
        for (Coin coin : coins) {
            try {
                syncNewTransactions(coin.getName());
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    /**
     * 同步一个币的新交易
     *
     * @param coinName 币种名称
     * @return 返回同步的区块个数
     * @throws Exception _
     */
    public int syncNewTransactions(String coinName) throws Exception {
        log.info("begin sync New usdt Transactions conname=" + coinName);
        Coin coin = coinService.getCoinByName(coinName);
        if (coin == null) {
            log.warn("do not find coin of name:" + coinName);
            return 0;
        }
        if (!CoinCategory.USDT.equals(coin.getCategory())) {
            log.error("coin category not match(want usdt),cannot sync block chain,name=" + coinName);
            return 0;
        }
        String url = "http://" + coin.getServerUser() + ':' + coin.getServerPassword() + "@" + coin.getServerAddress() + ":" + coin.getServerPort() + "/";
        OmniClient client = new OmniClient(url, 31);

        int blockNum = client.getBlockCount();
        // 获取上次同步到的区块高度，从上次同步到的高度+1开始同步
        String syncKey = ConfigKey.BC_LAST_SYNC_BLOCK_PRE + coinName;
        //int lastSyncBlock = configService.getConfigValue(syncKey, 0);
        int lastSyncBlock = configService.getConfigValue(syncKey, blockNum > 2000 ? blockNum - 2000 : 0);
        int curBlock = lastSyncBlock + 1;
        int toBlock = blockNum - txConfirmCount;
        log.info("begin sync,from=" + curBlock + ",toBlock=" + toBlock + ",coin=" + coin.getName() + ",base url=" + url);
        int count = 0;
        while (curBlock < toBlock) {
            try {
                syncBlock(coin, client, curBlock);
                curBlock++;
                count++;
                if (count >= 1000) {
                    log.warn("syn much usdt block,break,count:" + count);
                    break;
                }
            } catch (Exception ex) {
                log.error("sync usdt block failed:" + ex.getMessage(), ex);
                break;
            }
        }
        configService.setConfigValue(syncKey, Integer.toString(curBlock - 1));
        log.info("end syncNewTransactions,now lastSyncBlock=" + (curBlock - 1) + ",coin name=" + coinName + ",sync count=" + count);
        return count;
    }

    /**
     * 同步一个区块的交易
     *
     * @param coin        币种
     * @param client      客户端对象
     * @param blockHeight 同步的区块高度
     */
    private void syncBlock(Coin coin, OmniClient client, int blockHeight) {
        List<String> txids = client.omni_listBlockTransactions(blockHeight);
        for (String transactionHash : txids) {
            syncTransaction(coin, client, transactionHash);
        }
    }

    private void syncTransaction(Coin coin, OmniClient client, String transactionHash) {
        Map<String, Object> trans = client.omni_getTransaction(transactionHash);
        if (log.isTraceEnabled()) {
            log.trace("sync transaction:" + transactionHash + ", content=" + ReflectionToStringBuilder.toString(trans));
        }
        if (!trans.containsKey("blocktime"))
            return;
        if (!trans.containsKey("sendingaddress") || !trans.containsKey("referenceaddress") || !trans.containsKey("propertyid"))
            return;
        if (!trans.containsKey("txid") || !trans.containsKey("amount") || !trans.containsKey("type"))
            return;
        if (!"Simple Send".equals(trans.get("type").toString()))
            return;

        String valid = trans.get("valid").toString();
        if (!valid.equalsIgnoreCase("true")) {
            log.info("transactionHash:" + transactionHash + " is not valid");
            return;
        }
        String propertyid = trans.get("propertyid").toString();
        if (!propertyid.equals("31")) {
            return;
        }
        String hash = trans.get("txid").toString();
        String timereceived = trans.get("blocktime").toString();
        String fromAddress = trans.get("sendingaddress").toString();
        String toAddress = trans.get("referenceaddress").toString();
        String amount = trans.get("amount").toString();
        //String fee = trans.get("fee").toString();

        // 判断是否是平台地址
        Integer userId = userCoinService.getUserIdByCoinNameAndAddress(coin.getName(), toAddress);
        if (userId == null) return;
        // 检查数据通过
        log.info("find valid transaction:" + transactionHash + ", content=" + ReflectionToStringBuilder.toString(trans));

        // 如果交易已经存在，那么忽略
        if (transferService.isTransactionExists(coin.getName(), transactionHash)) {
            log.info("transaction:" + transactionHash + " already exists");
            return;
        }
        // 如果是内部转账，忽略
        Integer sendUserId = userCoinService.getUserIdByCoinNameAndAddress(coin.getName(), fromAddress);
        if (sendUserId != null) {
            log.info("inner transfer,ignore,tx=" + transactionHash);
            return;
        }

        BigDecimal recv = new BigDecimal(amount);
        BigDecimal fee = BigDecimal.ZERO;
        if (coin.getReceivedFee() < 1 && coin.getReceivedFee() > 0) {
            fee = recv.multiply(new BigDecimal(Float.toString(coin.getReceivedFee()))).setScale(8, RoundingMode.HALF_UP);
        }
        // 增加交易，直接成功
        transferService.addUserReceivedCoin(userId, coin.getName(), toAddress, hash, recv, fee, Integer.parseInt(timereceived));
        // 发送短信验证
        sendSms(configService, userService, smsService, recv, coin.getName(), userId);
    }


    ///////////////////////////////////////////////////////////////////////////
    private OmniClient getRpcClient(String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        Assert.check(!CoinCategory.USDT.equals(coin.getCategory()), ErrorCode.ERR_PARAM_ERROR);

        String url = "http://" + coin.getServerUser() + ':' + coin.getServerPassword() + "@" + coin.getServerAddress() + ":" + coin.getServerPort() + "/";
        try {
            return new OmniClient(url, 31);
        } catch (Exception e) {
            return null;
        }
    }

    public String getAccountAddress(int userId, String coinName) {
        UserCoinVO userCoin = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
        if (userCoin != null && userCoin.getBindAddress() != null && userCoin.getBindAddress().length() > 0) {
            return userCoin.getBindAddress();
        }
        OmniClient client = this.getRpcClient(coinName);
        if (client != null) {
            String address = client.omni_getAccountAddress(coinName + userId);
            if (address != null) {
                UserCoinVO tmp = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
                if (tmp == null) {
                    userCoinService.addUserCoin(userId, coinName, address);
                } else {
                    userCoinService.updateUserCoinAddress(userId, coinName, address);
                }
                return address;
            }
        }
        return null;
    }

    public double getAccountBalance(int userId, String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        UserCoinVO userCoin = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
        Assert.check(userCoin == null, ErrorCode.ERR_BTC_CLIENT_RPC_ERROR);

        OmniClient client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_BTC_CLIENT_RPC_ERROR);

        return client.omni_getBalance(userCoin.getBindAddress());
    }

    public String sendToAddress(int userId, String coinName, String toAddress, double amount) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        OmniClient client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_USDT_CLIENT_RPC_ERROR);

        String coinbase = coin.getCoinBase();
        Assert.check(coinbase == null || coinbase.length() <= 0, ErrorCode.ERR_COIN_BASE_ERROR);
        Assert.check(!client.validateAddress(toAddress).isValid(), ErrorCode.ERR_INVALID_ADDRESS);

        return client.omni_send(coinbase, toAddress, amount);
    }

    public int getTxConfirmCount(String coinName, String txid) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        OmniClient client = this.getRpcClient(coinName);
        Map<String, Object> m = client.omni_getTransaction(txid);
        String confirm = m.get("confirmations").toString();
        if (confirm != null) {
            return Integer.parseInt(confirm);
        } else {
            log.info("Txid:" + txid + ":null");
        }
        return 0;
    }

    public boolean isAddressValid(String coinName, String address) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        OmniClient client = this.getRpcClient(coinName);
        return client.validateAddress(address).isValid();
    }

    // 获取所有用户区块链上的余额总量
    public BigDecimal getAllUserBalanceInBlockChain(String coinName) throws Exception {
        List<String> addresses = userCoinService.getAllAddressByCoinName(coinName);
        if (addresses == null || addresses.size() == 0) {
            log.info("do not find any address of " + coinName);
            return BigDecimal.ZERO;
        }
        OmniClient client = getRpcClient(coinName);
        BigDecimal total = BigDecimal.ZERO;
        for (String address : addresses) {
            try {
                double balance = client.omni_getBalance(address);
                total = total.add(new BigDecimal(balance));
            } catch (Exception ex) {
                log.warn("get usdt balance failed", ex);
            }
        }
        return total;
    }
}
