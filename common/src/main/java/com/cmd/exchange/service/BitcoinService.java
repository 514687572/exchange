package com.cmd.exchange.service;

import com.cmd.exchange.blockchain.bitcoin.BitcoinJSONRPCClient;
import com.cmd.exchange.blockchain.bitcoin.BitcoinRException;
import com.cmd.exchange.blockchain.bitcoin.BitcoindRpcClient;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * 用于对比特币以及比特币山寨币系列的币种进行一些操作
 */
@Service
public class BitcoinService extends CoinServiceBase {
    private static Log log = LogFactory.getLog(BitcoinService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private TransferService transferService;
    // 交易确认的区块数
    private int txConfirmCount = 3;

    /**
     * 同步所有币的记录
     *
     * @return
     * @throws MalformedURLException
     */
    public void syncNewTransactions() {
        List<Coin> coins = coinService.getCoinsByCategory(CoinCategory.BTC);
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
     * @throws MalformedURLException 解析失败的时候返回
     */
    public int syncNewTransactions(String coinName) throws MalformedURLException {
        log.info("begin syncNewTransactions conname=" + coinName);
        Coin coin = coinService.getCoinByName(coinName);
        if (coin == null) {
            log.warn("do not find coin of name:" + coinName);
            return 0;
        }
        if (!CoinCategory.BTC.equals(coin.getCategory())) {
            log.error("coin category not match(want btc),cannot sync block chain,name=" + coinName);
            return 0;
        }
        String url = "http://" + coin.getServerUser() + ':' + coin.getServerPassword() + "@" + coin.getServerAddress() + ":" + coin.getServerPort() + "/";
        BitcoinJSONRPCClient client = new BitcoinJSONRPCClient(url);

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
                    log.warn("syn much block,break,count:" + count);
                    break;
                }
            } catch (Exception ex) {
                log.error("sync block failed:" + ex.getMessage(), ex);
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
    private void syncBlock(Coin coin, BitcoinJSONRPCClient client, int blockHeight) {
        String blockHash = client.getBlockHash(blockHeight);
        BitcoindRpcClient.Block block = client.getBlock(blockHash);
        List<String> transactionHashs = block.tx();
        for (String transactionHash : transactionHashs) {
            syncTransaction(coin, client, transactionHash);
        }
    }

    private void syncTransaction(Coin coin, BitcoinJSONRPCClient client, String transactionHash) {
        Map<String, Object> mTrans;
        try {
            mTrans = client.getTransactionEx(transactionHash);
            //Map<String, Object> mTrans = client.getRawTransaction(transactionHash);
        } catch (BitcoinRException ex) {
            if (log.isTraceEnabled()) {
                log.trace("getTransactionEx failed", ex);
            }
            if (ex.getResponseCode() == 500) {
                return;
            }
            throw ex;
        }
        if (log.isTraceEnabled()) {
            log.trace("sync transaction:" + transactionHash + ", content=" + ReflectionToStringBuilder.toString(mTrans));
        }
        if (!mTrans.containsKey("timereceived"))
            return;
        if (!mTrans.containsKey("details"))
            return;
        if (!mTrans.containsKey("txid"))
            return;
        //String timereceived = mTrans.get("timereceived").toString();
        List<Map<String, Object>> details = (List<Map<String, Object>>) mTrans.get("details");
        for (int i = 0; i < details.size(); i++) {
            Map<String, Object> detail = details.get(i);
            if (!detail.containsKey("category") || !detail.containsKey("address")
                    || !detail.containsKey("amount") || !detail.containsKey("account")) {
                continue;
            }

            String category = detail.get("category").toString();
            // 按时不处理发送的，只处理接收的
            boolean isReceive = "receive".equals(category);
            if (!isReceive) continue;
            String address = detail.get("address").toString();
            String amount = detail.get("amount").toString();
            String account = detail.get("account").toString();
            int time = Integer.parseInt(mTrans.get("time").toString());
            //String fee="0";

            if ("".equals(account)) continue;

            //if (!"receive".equals(category) && !"send".equals(category)) continue;

            /*if ("send".equals(category)){
                fee = detail.get("fee").toString();
                fee = double2string(Math.abs(new BigDecimal(fee).doubleValue()));
            }*/

            //TODO 判断是否是此平台账户，暂时不判断
            // 判断是否是平台地址
            Integer userId = userCoinService.getUserIdByCoinNameAndAddress(coin.getName(), address);
            if (userId == null) continue;
            // 检查数据通过

            log.info("find valid transaction:" + transactionHash + ", content=" + ReflectionToStringBuilder.toString(mTrans));

            // 如果交易已经存在，那么忽略
            if (transferService.isTransactionExists(coin.getName(), transactionHash)) {
                log.info("transaction:" + transactionHash + " already exists");
                continue;
            }
            BigDecimal recv = new BigDecimal(amount);
            BigDecimal fee = BigDecimal.ZERO;
            if (coin.getReceivedFee() < 1 && coin.getReceivedFee() > 0) {
                fee = recv.multiply(new BigDecimal(Float.toString(coin.getReceivedFee()))).setScale(8, RoundingMode.HALF_UP);
            }

            //if(isReceive) {
            // 增加交易，直接成功
            transferService.addUserReceivedCoin(userId, coin.getName(), address, transactionHash, recv, fee, time);
            sendSms(configService, userService, smsService, recv, coin.getName(), userId);
            //}
        }
    }


    /////////////////////////////////////////////////////////////////////////////
    private BitcoinJSONRPCClient getRpcClient(String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        Assert.check(!CoinCategory.BTC.equals(coin.getCategory()), ErrorCode.ERR_PARAM_ERROR);

        String url = "http://" + coin.getServerUser() + ':' + coin.getServerPassword() + "@" + coin.getServerAddress() + ":" + coin.getServerPort() + "/";
        try {
            return new BitcoinJSONRPCClient(url);
        } catch (Exception e) {
            return null;
        }
    }

    public String getAccountAddress(int userId, String coinName) {
        UserCoinVO userCoin = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
        if (userCoin != null && userCoin.getBindAddress() != null && userCoin.getBindAddress().length() > 0) {
            return userCoin.getBindAddress();
        }
        BitcoinJSONRPCClient client = this.getRpcClient(coinName);
        if (client != null) {
            List<String> list = client.getAddressesByAccount(coinName + userId);
            String address = null;
            if (list.size() > 0) {
                address = list.get(0);
            } else {
                address = client.getAccountAddress(coinName + userId);
            }

            if (address != null) {
                UserCoinVO coin1 = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
                if (coin1 == null) {
                    userCoinService.addUserCoin(userId, coinName, address);
                } else {
                    userCoinService.updateUserCoinAddress(userId, coinName, address);
                }
                return address;
            }

        }
        return null;
    }

    /**
     * 获取该用户币种余额
     *
     * @param userId
     * @param coinName
     * @return
     */
    public double getAccountBalance(int userId, String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        BitcoindRpcClient client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_BTC_CLIENT_RPC_ERROR);

        return client.getBalance(coinName + userId);
    }

    public String sendToAddress(int userId, String coinName, String toAddress, double amount) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        BitcoindRpcClient client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_BTC_CLIENT_RPC_ERROR);
        Assert.check(!client.validateAddress(toAddress).isValid(), ErrorCode.ERR_INVALID_ADDRESS);

        return client.sendToAddress(toAddress, amount);
    }

    public int getTxConfirmCount(String coinName, String txid) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        BitcoindRpcClient client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_BTC_CLIENT_RPC_ERROR);

        Map<String, Object> mTrans;
        try {
            mTrans = client.getTransactionEx(txid);
            //Map<String, Object> mTrans = client.getRawTransaction(transactionHash);
        } catch (BitcoinRException ex) {
            if (log.isTraceEnabled()) {
                log.trace("getTransactionEx failed", ex);
            }
            if (ex.getResponseCode() == 500) {
                return 0;
            }
            throw ex;
        }

        String confirm = mTrans.get("confirmations").toString();
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

        BitcoindRpcClient client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_BTC_CLIENT_RPC_ERROR);
        return client.validateAddress(address).isValid();
    }

    // 获取所有用户区块链上的余额总量
    public BigDecimal getAllUserBalanceInBlockChain(String coinName) throws Exception {
        BitcoindRpcClient client = this.getRpcClient(coinName);
        return new BigDecimal(client.getBalance()).setScale(8, RoundingMode.HALF_UP);
    }
}
