package com.cmd.exchange.service;

import com.cmd.exchange.blockchain.bitcoin.BitcoindRpcClient;
import com.cmd.exchange.blockchain.bitcoin.JSON;
import com.cmd.exchange.blockchain.eth.ETHCoin;
import com.cmd.exchange.blockchain.eth.ETHHelper;
import com.cmd.exchange.blockchain.eth.EthUtils;
import com.cmd.exchange.common.constants.CoinCategory;
import com.cmd.exchange.common.constants.ConfigKey;
import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.CoinConfigMapper;
import com.cmd.exchange.common.mapper.EthAddressMapper;
import com.cmd.exchange.common.model.Coin;
import com.cmd.exchange.common.model.CoinConfig;
import com.cmd.exchange.common.model.EthAddress;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.RandomUtil;
import com.cmd.exchange.common.vo.UserCoinVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

/**
 * 用于对比特币以及比特币山寨币系列的币种进行一些操作
 */
@Service
public class EthService extends CoinServiceBase {
    private static Log log = LogFactory.getLog(EthService.class);
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
    private EthAddressMapper ethAddressMapper;
    // 交易确认的区块数
    private int txConfirmCount = 12;

    /**
     * 同步所有以太坊系列的交易
     */
    public void syncNewTransactions() {
        // 根据主机和端口进行分开同步
        List<Coin> coins = coinService.getAllEthCoins();
        List<String> completeCoins = new ArrayList<String>();
        for (Coin coin : coins) {
            if (coin.getServerAddress() == null || coin.getServerAddress().trim().length() == 0) {
                continue;
            }
            String idStr = coin.getServerAddress().trim() + ":" + coin.getServerPort();
            if (completeCoins.contains(idStr)) {
                continue;
            }
            try {
                syncNewTransactions(coin.getServerAddress(), coin.getServerPort());
            } catch (Exception ex) {
                log.error("", ex);
            }
            completeCoins.add(idStr);
        }
    }

    public void updateEthDecimal(String host, int port, Coin coin) throws Exception {
        //Web3j web3 = Web3j.build(new HttpService("http://" + "127.0.0.1" + ":" + 8845));
        Web3j web3 = Web3j.build(new HttpService("http://" + host + ":" + port));
        Credentials credentials = Credentials.create("d3e9d64f6c244156b77c58bb4dc7a766006453ab6d470175c9b952edf19d11bc");
        ETHCoin ethCoin = new ETHCoin(coin.getContractAddress(), web3, credentials, new BigInteger("1000"), new BigInteger("10000"));
        //int decimals = 18;
        int decimals = ethCoin.decimals().send().intValue();
        log.info("coin " + coin.getName() + " decimals=" + decimals);
        coin.setDecimals(decimals);
    }

    /**
     * 同步所有指定服务器和端口的以太坊币的新交易
     *
     * @return 返回同步的区块个数
     */
    public int syncNewTransactions(String host, int port) throws Exception {
        log.info("begin syncNewTransactions(eth), host=" + host + ",port=" + port);
        List<Coin> coins = coinService.getAllEthCoins();
        if (coins == null || coins.size() == 0) {
            log.warn("do not find any eth coins");
            return 0;
        }
        HashMap<String, Coin> coinMap = new HashMap<String, Coin>();
        Coin ethCoin = null;
        for (Coin coin : coins) {
            // 这只合约地址，简化后面的判断
            if (CoinCategory.TOKEN.equals(coin.getCategory())) {
                if (coin.getContractAddress() != null && coin.getContractAddress().trim().length() > 16 && coin.getServerAddress() != null) {
                    if (host.equals(coin.getServerAddress().trim()) && port == coin.getServerPort()) {
                        coinMap.put(coin.getContractAddress().trim().toLowerCase(), coin);
                        updateEthDecimal(host, port, coin);
                    }
                }
            } else {
                if (host.equals(coin.getServerAddress().trim()) && port == coin.getServerPort()) {
                    if (ethCoin != null) {
                        log.error("find duplicate eth coin");
                    }
                    ethCoin = coin;
                }
            }
        }
        if (coinMap.size() == 0) {
            log.warn("do not find any match coins, host=" + host + ",port=" + port);
        }
        log.info("sync eth coins:" + JSON.stringify(coinMap) + ",eth=" + ReflectionToStringBuilder.toString(ethCoin != null ? ethCoin : "none"));
        Web3j web3 = Web3j.build(new HttpService("http://" + host + ":" + port));

        // 获取上次同步到的区块高度，从上次同步到的高度+1开始同步
        String syncKey = ConfigKey.BC_LAST_SYNC_ETH_PRE + host + ":" + port;
        int blockNum = web3.ethBlockNumber().send().getBlockNumber().intValue();
        // 如果没有值，从昨天开始同步
        int lastSyncBlock = configService.getConfigValue(syncKey, blockNum > 3000 ? blockNum - 3000 : 0);
        if (lastSyncBlock > 100) {
            // 倒退100个块重复同步，为了避免出现区块链同步导致后面的块同步到了前面有块没同步到的问题
            lastSyncBlock = lastSyncBlock - 100;
        }
        int curBlock = lastSyncBlock + 1;
        int toBlock = blockNum - txConfirmCount;
        log.info("begin sync,from=" + curBlock + ",toBlock=" + toBlock + ",host=" + host + ",port=" + port);
        int count = 0;
        while (curBlock < toBlock) {
            try {
                syncBlock(ethCoin, coinMap, web3, curBlock);
                count++;
                curBlock++;
                if (count >= 1000) {
                    log.warn("syn much block,break,count:" + count);
                    break;
                }
            } catch (Exception ex) {
                log.error("sync eth block failed:" + ex.getMessage(), ex);
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
     * @param coinMap     智能合约的币
     * @param web3        客户端对象
     * @param blockNumber 同步的区块号
     */
    private void syncBlock(Coin ethCoin, HashMap<String, Coin> coinMap, Web3j web3, int blockNumber) throws IOException {
        EthBlock.Block block = web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)), true).send().getBlock();
        List<EthBlock.TransactionResult> list = block.getTransactions();
        for (int i = 0; i < list.size(); i++) {
            Transaction transaction = (Transaction) list.get(i);
            syncTransaction(ethCoin, coinMap, web3, transaction, block);
        }
    }

    private void syncTransaction(Coin ethCoin, HashMap<String, Coin> coinMap, Web3j web3, Transaction transaction, EthBlock.Block block) throws IOException {
        if (BigInteger.valueOf(0).equals(transaction.getValue()) && !"0x".equals(transaction.getInput().toString())) {
            syncTokenTransaction(coinMap, web3, transaction, block);
        } else {
            if (ethCoin == null) return;
            if (transaction.getTo() == null || !CoinCategory.ETH.equals(ethCoin.getCategory())) {
                return;
            }
            // 判断是否是平台地址
            Integer userId = userCoinService.getUserIdByCoinNameAndAddress(ethCoin.getName(), transaction.getTo());
            if (userId == null) {
                EthAddress ethAddress = ethAddressMapper.getEthAddressByAddress(transaction.getTo());
                if (ethAddress == null || ethAddress.getUserId() == null) return;
                log.info("use user other address " + ethAddress);
                userId = ethAddress.getUserId();
            }
            // 检查数据通过
            log.info("find valid transaction,coin=" + ethCoin.getName() + ", content=" + ReflectionToStringBuilder.toString(transaction));
            // 如果是内部转账，属于收集币的行为，忽略
            Integer sendUserId = userCoinService.getUserIdByCoinNameAndAddress(ethCoin.getName(), transaction.getFrom());
            if (sendUserId != null) {
                log.info("inner transfer,ignore,tx=" + transaction.getHash());
                return;
            }

            // 如果交易已经存在，那么忽略
            if (transferService.isTransactionExists(ethCoin.getName(), transaction.getHash())) {
                log.info("transaction:" + transaction.getHash() + " already exists");
                return;
            }
            // 增加交易，直接成功
            BigDecimal value = new BigDecimal(transaction.getValue());
            value = value.divide(new BigDecimal("1000000000000000000"), 8, RoundingMode.HALF_UP);
            BigDecimal fee = BigDecimal.ZERO;
            if (ethCoin.getReceivedFee() < 1 && ethCoin.getReceivedFee() > 0) {
                fee = value.multiply(new BigDecimal(Float.toString(ethCoin.getReceivedFee()))).setScale(8, RoundingMode.HALF_UP);
            }
            transferService.addUserReceivedCoin(userId, ethCoin.getName(), transaction.getTo(), transaction.getHash(),
                    value, fee, block.getTimestamp().intValue());
            sendSms(configService, userService, smsService, value, ethCoin.getName(), userId);
        }
    }

    private void syncTokenTransaction(HashMap<String, Coin> coinMap, Web3j web3, Transaction transaction, EthBlock.Block block) throws IOException {
        if (transaction.getTo() == null) {
            return;
        }
        Coin coin = coinMap.get(transaction.getTo().toLowerCase());
        if (coin == null) {
            return;
        }
        // 处理 "a9059cbb": "transfer(address,uint256)",和 "23b872dd": "transferFrom(address,address,uint256)"这2个函数
        String input = transaction.getInput();
        if (!input.startsWith("0xa9059cbb") && !input.startsWith("0x23b872dd")) {
            return;
        }
        Optional<TransactionReceipt> optional = web3.ethGetTransactionReceipt(transaction.getHash()).send().getTransactionReceipt();
        TransactionReceipt receipt = optional.orElse(null);
        if (receipt == null) {
            log.warn("get receipt failed:" + ReflectionToStringBuilder.toString(transaction));
            return;
        }
        if (receipt.getGasUsed().compareTo(transaction.getGas()) >= 0) {
            log.debug("transaction failed:" + ReflectionToStringBuilder.toString(transaction) + ",receipt:" + ReflectionToStringBuilder.toString(receipt));
            return;
        }
        if (receipt.getContractAddress() != null)
            return;
        for (int j = 0; j < receipt.getLogs().size(); j++) {
            org.web3j.protocol.core.methods.response.Log ethLog = receipt.getLogs().get(j);
            if (ethLog.getTopics().size() != 3) {
                continue;
            }
            String data = ethLog.getData().substring(2);
            if (data.length() == 0) {
                continue;
            }
            BigDecimal value = new BigDecimal(new BigInteger(data, 16));
            String from = ethLog.getTopics().get(1);
            from = "0x" + from.substring(from.length() - 40);
            String to = ethLog.getTopics().get(2);
            to = "0x" + to.substring(to.length() - 40);

            // 判断是否是平台地址
            Integer userId = userCoinService.getUserIdByCoinNameAndAddress(coin.getName(), to);
            if (userId == null) {
                EthAddress ethAddress = ethAddressMapper.getEthAddressByAddress(to);
                if (ethAddress == null || ethAddress.getUserId() == null) return;
                log.info("use user other address(token) " + ethAddress);
                userId = ethAddress.getUserId();
            }
            ;
            // 检查数据通过
            log.info("find valid transaction,coin=" + coin.getName() + ", content=" + ReflectionToStringBuilder.toString(transaction));
            // 如果是内部转账，属于收集币的行为，忽略
            Integer sendUserId = userCoinService.getUserIdByCoinNameAndAddress(coin.getName(), transaction.getFrom());
            if (sendUserId != null) {
                log.info("inner transfer token,ignore,tx=" + transaction.getHash());
                return;
            }
            // 如果交易已经存在，那么忽略
            if (transferService.isTransactionExists(coin.getName(), transaction.getHash())) {
                log.info("transaction:" + transaction.getHash() + " already exists");
                return;
            }
            // 增加交易，直接成功
            value = value.divide(new BigDecimal("10").pow(coin.getDecimals()), 8, RoundingMode.HALF_UP);
            BigDecimal fee = BigDecimal.ZERO;
            if (coin.getReceivedFee() < 1 && coin.getReceivedFee() > 0) {
                fee = value.multiply(new BigDecimal(Float.toString(coin.getReceivedFee()))).setScale(8, RoundingMode.HALF_UP);
            }

            transferService.addUserReceivedCoin(userId, coin.getName(), to, transaction.getHash(),
                    value, fee, block.getTimestamp().intValue());
            sendSms(configService, userService, smsService, value, coin.getName(), userId);
            break;
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    private ETHHelper getRpcClient(String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);
        Assert.check(!CoinCategory.ETH.equals(coin.getCategory()) && !CoinCategory.TOKEN.equals(coin.getCategory()), ErrorCode.ERR_PARAM_ERROR);

        String url = "http://" + coin.getServerAddress() + ":" + coin.getServerPort();
        log.info(url);
        return new ETHHelper(url, coinName);
    }

    public String getAccountAddress(int userId, String coinName) {
        //代币和ETH公用一个地址
        EthAddress ethAddress = userCoinService.getEthAddressByUserId(userId);
        if (ethAddress == null) {
            ETHHelper client = this.getRpcClient(coinName);
            if (client != null) {
                String pwd = RandomUtil.getCode(10);
                String filename = client.createWallet(pwd);
                String credentials = client.getCredentialsByFileName(filename);
                String address = client.getCredentials(filename, pwd).getAddress();
                if (address != null) {
                    userCoinService.addEthAddress(new EthAddress().setUserId(userId).setAddress(address).setPassword(pwd).setFilename(filename).setCredentials(credentials));
                }
            }
        }
        EthAddress ethAddr = userCoinService.getEthAddressByUserId(userId);
        if (ethAddr != null) {
            UserCoinVO userCoin = userCoinService.getUserCoinByUserIdAndCoinName(userId, coinName);
            if (userCoin == null) {
                userCoinService.addUserCoin(userId, coinName, ethAddr.getAddress());
            } else {
                if (userCoin.getBindAddress() == null || userCoin.getBindAddress().trim().length() == 0) {
                    userCoinService.updateUserCoinAddress(userId, coinName, ethAddr.getAddress());
                }
            }
            return ethAddr.getAddress();
        }
        return null;
    }

    public double getAccountBalance(int userId, String coinName) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        EthAddress ethAddress = ethAddressMapper.getEthAddressByUserId(userId);
        Assert.check(ethAddress == null, ErrorCode.ERR_COIN_BASE_ERROR);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);

        if (CoinCategory.ETH.equals(coin.getCategory())) {
            return client.getBalanceEx(ethAddress.getCredentials(), ethAddress.getPassword()).doubleValue();
        } else if (CoinCategory.TOKEN.equals(coin.getCategory())) {
            return client.contractBalanceEx(ethAddress.getCredentials(), ethAddress.getPassword(), coin.getContractAddress()).doubleValue();
        }
        return 0;
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
        return sendToAddress(ethAddress, coinName, toAddress, amount, null);
    }

    public String sendToAddress(EthAddress ethAddress, String coinName, String toAddress, double amount, BigInteger gasPrice) {
        boolean bCoinBase = false;
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);
        Assert.check(!client.isValidAddress(toAddress), ErrorCode.ERR_INVALID_ADDRESS);

        String coinbase = coin.getCoinBase();
        Assert.check(coinbase == null || coinbase.length() <= 0, ErrorCode.ERR_COIN_BASE_ERROR);

        if (ethAddress == null) {
            ethAddress = ethAddressMapper.getEthAddressByAddress(coinbase);
            Assert.check(ethAddress == null, ErrorCode.ERR_COIN_BASE_ERROR);
            bCoinBase = true;
        }

        if (CoinCategory.ETH.equals(coin.getCategory())) {
            //eth发送
            bCoinBase = false;
            if (bCoinBase == false) {
                return client.transferEx(ethAddress.getCredentials(), ethAddress.getPassword(), toAddress, amount);
            } else {
                BigInteger gasPrice1 = new BigInteger(configService.getConfigValue(ConfigKey.ETH_GAS_PRICE, "60000000000"));
                BigInteger gasLimit = new BigInteger(configService.getConfigValue(ConfigKey.ETH_GAS_LIMIT, "60000"));
                return client.transferEx(ethAddress.getCredentials(), ethAddress.getPassword(), toAddress, amount, gasPrice1, gasLimit);
            }
        } else if (CoinCategory.TOKEN.equals(coin.getCategory())) {
            //代币发送
            int decimal = client.contractDecimalEx(ethAddress.getCredentials(), ethAddress.getPassword(), coin.getContractAddress());
            return client.contractTransferEx(ethAddress.getCredentials(), ethAddress.getPassword(), coin.getContractAddress(), toAddress, amount, decimal, gasPrice);
        }
        return null;
    }

    /**
     * 获取比指定id大的最小的一个id的地址
     *
     * @param id 指定id
     * @return 比id大的最小id的地址或者null
     */
    public EthAddress getNextAddress(int id) {
        return ethAddressMapper.getNextAddress(id);
    }

    public EthAddress getEthAddressByAddress(String address) {
        return ethAddressMapper.getEthAddressByAddress(address);
    }

    // 获取所有用户区块链上的余额总量
    public BigDecimal getAllUserBalanceInBlockChain(String coinName) throws Exception {
        List<String> addresses = userCoinService.getAllAddressByCoinName(coinName);
        if (addresses == null || addresses.size() == 0) {
            log.info("do not find any address of " + coinName);
            return BigDecimal.ZERO;
        }
        Coin coin = coinService.getCoinByName(coinName);
        boolean isToken = CoinCategory.TOKEN.equalsIgnoreCase(coin.getCategory());
        String url = "http://" + coin.getServerAddress() + ":" + coin.getServerPort();
        log.info("begin get balance:" + url);
        ETHHelper client = new ETHHelper(url, coinName);
        Web3j web3 = client.getWeb3();
        // 获取信息，验证区块链可用
        client.getBlockNumber();
        BigDecimal total = BigDecimal.ZERO;
        if (!isToken) {
            for (String address : addresses) {
                try {
                    EthGetBalance getBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
                    BigDecimal balance = Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
                    total = total.add(balance);
                } catch (Exception ex) {
                    log.warn("get balance failed", ex);
                }
            }
        } else {
            // 代币
            updateEthDecimal(coin.getServerAddress(), coin.getServerPort(), coin);
            for (String address : addresses) {
                try {
                    // 查询余额是不需要提供私钥的，这里随便写一个
                    Credentials credentials = EthUtils.loadCredentialsByContect("1249418731", "{\"address\":\"17dfaad634c4521fa2d9ec3cc4112451a0235857\",\"id\":\"2eb5dca6-9028-4f7c-bf99-00c7a39db55f\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"006a62779fe48bac92b78fe99ae2ed2705a5e96f56f6ed692c38528802ba166c\",\"cipherparams\":{\"iv\":\"55fc586576f5d800e478e2a2d7cbf50a\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"da18243bde9086192105a85dcb56504ee6b576b0920e01d9aad64eb7117d366e\"},\"mac\":\"761a0804a326b457564e8a833e30d3484335ad58d2ec2906a6cdfa492b083c85\"}}");
                    ETHCoin ethCoin = ETHCoin.load(coin.getContractAddress(),
                            web3,
                            new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                            Contract.GAS_PRICE,
                            Contract.GAS_LIMIT);
                    BigInteger biBalance = ethCoin.balanceOf(address).send();
                    total = total.add(new BigDecimal(biBalance));
                } catch (Exception ex) {
                    log.warn("get balance failed", ex);
                }
            }
            // 转换为正确的显示（有小数的）
            total = total.divide(new BigDecimal("10").pow(coin.getDecimals()), 8, RoundingMode.HALF_UP);
        }
        return total;
    }

    public boolean isAddressValid(String coinName, String address) {
        Coin coin = coinService.getCoinByName(coinName);
        Assert.check(coin == null, ErrorCode.ERR_DB_CONFIG_NOT_EXIST);

        ETHHelper client = this.getRpcClient(coinName);
        Assert.check(client == null, ErrorCode.ERR_ETH_CLIENT_RPC_ERROR);
        return client.isValidAddress(address);
    }
}
