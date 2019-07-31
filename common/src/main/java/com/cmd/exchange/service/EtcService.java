package com.cmd.exchange.service;

import com.cmd.exchange.blockchain.eth.ETHHelper;
import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.EtcAddressMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.EthAddress;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.RandomUtil;
import com.cmd.exchange.common.vo.UserCoinVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 用于对比特币以及比特币山寨币系列的币种进行一些操作
 */
@Service
public class EtcService extends CoinServiceBase {
    private static Log log = LogFactory.getLog(EtcService.class);
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
    @Autowired
    private EtcAddressMapper etcAddressMapper;

    // 交易确认的区块数
    private int txConfirmCount = 12;

    /**
     * 同步所有以太坊系列的交易
     */
    public void syncNewTransactions() {
        // 根据主机和端口进行分开同步
        List<Coin> coins = coinService.getCoinsByCategory(CoinCategory.ETC);
        List<String> completeCoins = new ArrayList<String>();
        for (Coin coin : coins) {
            try {
                syncNewTransactions(coin, coin.getServerAddress(), coin.getServerPort());
            } catch (Exception ex) {
                log.error(" ", ex);
            }
        }
    }

    /**
     * 同步所有指定服务器和端口的以太坊币的新交易
     *
     * @return 返回同步的区块个数
     */
    public int syncNewTransactions(Coin coin, String host, int port) throws Exception {
        log.info("begin syncNewTransactions(etc), host=" + host + ",port=" + port);
        Web3j web3 = Web3j.build(new HttpService("http://" + host + ":" + port));

        // 获取上次同步到的区块高度，从上次同步到的高度+1开始同步
        String syncKey = ConfigKey.BC_LAST_SYNC_BLOCK_PRE + coin.getName();
        int blockNum = web3.ethBlockNumber().send().getBlockNumber().intValue();
        // 如果没有值，从昨天开始同步
        int lastSyncBlock = configService.getConfigValue(syncKey, blockNum > 3000 ? blockNum - 3000 : 0);
        int curBlock = lastSyncBlock + 1;
        int toBlock = blockNum - txConfirmCount;
        log.info("begin sync,from=" + curBlock + ",toBlock=" + toBlock + ",host=" + host + ",port=" + port);
        int count = 0;
        while (curBlock < toBlock) {
            try {
                syncBlock(coin, web3, curBlock);
                count++;
                curBlock++;
                if (count >= 1000) {
                    log.warn("syn much block,break,count:" + count);
                    break;
                }
            } catch (Exception ex) {
                log.error("sync etc block failed:" + ex.getMessage(), ex);
                break;
            }
        }
        configService.setConfigValue(syncKey, Integer.toString(curBlock - 1));
        log.info("end syncNewTransactions,now lastSyncBlock=" + (curBlock - 1) + ",host=" + host + ",port=" + port + ",sync count=" + count);
        return count;
    }

    /**
     * 同步一个区块的交易
     *
     * @param ethCoin     币种
     * @param web3        客户端对象
     * @param blockNumber 同步的区块号
     */
    private void syncBlock(Coin ethCoin, Web3j web3, int blockNumber) throws IOException {
        EthBlock.Block block = web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), true).send().getBlock();
        List<EthBlock.TransactionResult> list = block.getTransactions();
        for (int i = 0; i < list.size(); i++) {
            Transaction transaction = (Transaction) list.get(i);
            syncTransaction(ethCoin, web3, transaction, block);
        }
    }

    private void syncTransaction(Coin etcCoin, Web3j web3, Transaction transaction, EthBlock.Block block) throws IOException {
        // 判断是否是平台地址
        if (transaction.getValue().intValue() == 0) return;
        Integer userId = userCoinService.getUserIdByCoinNameAndAddress(etcCoin.getName(), transaction.getTo());
        if (userId == null) return;
        // 检查数据通过
        log.info("find valid transaction,coin=" + etcCoin.getName() + ", content=" + ReflectionToStringBuilder.toString(transaction));
        // 如果交易已经存在，那么忽略
        if (transferService.isTransactionExists(etcCoin.getName(), transaction.getHash())) {
            log.info("transaction:" + transaction.getHash() + " already exists");
            return;
        }
        // 增加交易，直接成功
        BigDecimal value = new BigDecimal(transaction.getValue());
        value = value.divide(new BigDecimal("1000000000000000000"), 8, RoundingMode.HALF_UP);
        BigDecimal fee = BigDecimal.ZERO;
        if (etcCoin.getReceivedFee() < 1 && etcCoin.getReceivedFee() > 0) {
            fee = value.multiply(new BigDecimal(Float.toString(etcCoin.getReceivedFee()))).setScale(8, RoundingMode.HALF_UP);
        }
        transferService.addUserReceivedCoin(userId, etcCoin.getName(), transaction.getTo(), transaction.getHash(),
                value, fee, block.getTimestamp().intValue());
        sendSms(configService, userService, smsService, value, etcCoin.getName(), userId);
    }


    //////////////////////////////////////////////////////////////////////////////
    private ETHHelper getRpcClient(String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        Assert.check(!CoinCategory.ETC.equals(coin.getCategory()), ErrorCode.ERR_PARAM_ERROR);

        String url = "http://" + coin.getServerAddress() + ":" + coin.getServerPort();
        return new ETHHelper(url, coinName);
    }

    public String getAccountAddress(int userId, String coinName) {
        //代币和ETH公用一个地址
        UserCoinVO ethAddress = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
        if (ethAddress != null) {
            return ethAddress.getBindAddress();
        }
        ETHHelper client = this.getRpcClient(coinName);
        if (client != null) {
            String pwd = RandomUtil.getCode(10);
            String filename = client.createWallet(pwd);
            String credentials = client.getCredentialsByFileName(filename);
            String address = client.getCredentials(filename, pwd).getAddress();
            if (address != null) {
                userCoinService.addEtcAddress(new EthAddress().setUserId(userId).setAddress(address).setPassword(pwd).setFilename(filename).setCredentials(credentials));
            }
            userCoinService.addUserCoin(userId, coinName, address);
            return address;
        }
        return null;
    }

    public double getAccountBalance(int userId, String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        EthAddress etcAddress = etcAddressMapper.getEtcAddressByUserId(userId);
        Assert.check(etcAddress == null, ErrorCode.ERR_COIN_BASE_ERROR);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);

        return client.getBalanceEx(etcAddress.getCredentials(), etcAddress.getPassword()).doubleValue();
    }

    /**
     * 发送以太币/以太坊代币到指定地址（使用基本账户发送）
     *
     * @param userId
     * @param coinName
     * @param toAddress
     * @param amount
     * @return
     */
    public String sendToAddress(int userId, String coinName, String toAddress, double amount) {
        return sendToAddress(null, coinName, toAddress, amount);
    }

    public int getTxConfirmCount(String coinName, String txid) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);

        BigInteger blockNumber = client.getBlockNumber();
        BigInteger txBlock = client.getTransaction(txid).getBlockNumber();
        Assert.check(txBlock == null || txBlock.intValue() <= 0, ErrorCode.ERR_INVALID_TXID);

        return blockNumber.subtract(txBlock).intValue();
    }

    /**
     * 发送以太币/以太坊代币到指定地址（使用指定地址发送发送）
     *
     * @param ethAddress
     * @param coinName
     * @param toAddress
     * @param amount
     * @return
     */
    public String sendToAddress(EthAddress ethAddress, String coinName, String toAddress, double amount) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);
        Assert.check(!client.isValidAddress(toAddress), ErrorCode.ERR_INVALID_ADDRESS);

        String coinbase = coin.getCoinBase();
        Assert.check(coinbase == null || coinbase.length() <= 0, ErrorCode.ERR_COIN_BASE_ERROR);

        if (ethAddress == null) {
            ethAddress = etcAddressMapper.getEtcAddressByAddress(coinbase);
            Assert.check(ethAddress == null, ErrorCode.ERR_COIN_BASE_ERROR);
        }

        return client.transferEx(ethAddress.getCredentials(), ethAddress.getPassword(), toAddress, amount);
    }

    /**
     * 获取比指定id大的最小的一个id的地址
     *
     * @param id 指定id
     * @return 比id大的最小id的地址或者null
     */
    public EthAddress getNextAddress(int id) {
        return etcAddressMapper.getNextAddress(id);
    }

    public boolean isAddressValid(String coinName, String address) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);
        return client.isValidAddress(address);
    }

    // 获取所有用户区块链上的余额总量
    public BigDecimal getAllUserBalanceInBlockChain(String coinName) throws Exception {
        List<String> addresses = userCoinService.getAllAddressByCoinName(coinName);
        if (addresses == null || addresses.size() == 0) {
            log.info("do not find any address of " + coinName);
            return BigDecimal.ZERO;
        }
        Coin coin = coinService.getCoinByName(coinName);
        String url = "http://" + coin.getServerAddress() + ":" + coin.getServerPort();
        log.info("begin get balance:" + url);
        ETHHelper client = new ETHHelper(url, coinName);
        Web3j web3 = client.getWeb3();
        // 获取信息，验证区块链可用
        client.getBlockNumber();
        BigDecimal total = BigDecimal.ZERO;
        for (String address : addresses) {
            try {
                EthGetBalance getBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
                BigDecimal balance = Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
                total = total.add(balance);
            } catch (Exception ex) {
                log.warn("get etc balance failed", ex);
            }
        }
        return total;
    }
}
