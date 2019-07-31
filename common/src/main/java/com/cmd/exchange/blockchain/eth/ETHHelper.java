package com.cmd.exchange.blockchain.eth;

import com.cmd.exchange.common.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
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
import org.web3j.tx.Transfer;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class ETHHelper {
    private static Logger logger = LoggerFactory.getLogger(ETHHelper.class);

    private static final BigInteger gasLimit = Convert.toWei(BigDecimal.valueOf(900000), Convert.Unit.WEI).toBigInteger();

    private String keystore = "./wallet";

    private String coinName;

    private Web3j web3;

    public Web3j getWeb3() {
        return this.web3;
    }

    public ETHHelper(String client, String name) {
        this.web3 = Web3j.build(new HttpService(client));
        this.coinName = name;
    }

    public String getCoinName() {
        return coinName;
    }

    //根据秘钥文件名获操作钱包
    //创建钱包
    public String createWallet(String password) {
        File destinationDirectory = new File(keystore);
        try {
            return WalletUtils.generateNewWalletFile(password, destinationDirectory, true);
        } catch (Exception e) {
            throw new ServerException("创建以太坊钱包错误，错误原因：" + e.getMessage(), e);
        }
    }

    //获取凭证
    public Credentials getCredentials(String fileName, String password) {
        try {
            return WalletUtils.loadCredentials(password, keystore + "/" + fileName);
        } catch (Exception e) {
            throw new ServerException("加载以太坊钱包错误，错误原因：" + e.getMessage(), e);
        }
    }

    //以太坊智能合约获取余额
    public BigDecimal contractBalance(String fileName, String password, String contractAddress) {
        try {
            Credentials credentials = this.getCredentials(fileName, password);
            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    Contract.GAS_PRICE,
                    Contract.GAS_LIMIT);
            BigInteger balance = ethCoin.balanceOf(credentials.getAddress()).send();
            return Convert.fromWei(BigDecimal.valueOf(balance.doubleValue()), Convert.Unit.ETHER);
        } catch (Exception e) {
            throw new ServerException("以太坊代币获取余额错误，错误原因：" + e.getMessage(), e);
        }
    }

    // 以太坊只能合约转账
    public String contractTransfer(String fileName, String password, String contractAddress, String to, double value) {
        try {
            Credentials credentials = this.getCredentials(fileName, password);

            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    Contract.GAS_PRICE,
                    Contract.GAS_LIMIT);
            TransactionReceipt receipt = ethCoin.transfer(to, Convert.toWei(BigDecimal.valueOf(value), Convert.Unit.ETHER).toBigInteger()).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊智能合约转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    // 以太坊只能合约转账,指定price, limit
    public String contractTransfer(String fileName, String password, String contractAddress, String to, double value, BigInteger gasPrice, BigInteger gasLimit) {
        try {
            Credentials credentials = this.getCredentials(fileName, password);

            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    gasPrice,
                    gasLimit);
            TransactionReceipt receipt = ethCoin.transfer(to, Convert.toWei(BigDecimal.valueOf(value), Convert.Unit.ETHER).toBigInteger()).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊智能合约转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    //获取ETH余额
    public BigDecimal getBalance(String fileName, String password) {
        try {
            Credentials credentials = this.getCredentials(fileName, password);
            EthGetBalance getBalance = web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            return Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
        } catch (Exception e) {
            throw new ServerException("获取以太坊余额错误，错误原因：" + e.getMessage(), e);
        }
    }

    //转账ETH
    public String transfer(String fileName, String password, String to, double value) {
        try {
            Credentials credentials = this.getCredentials(fileName, password);

            Transfer transfer = new Transfer(web3, new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)));
            TransactionReceipt receipt = transfer.sendFunds(to, BigDecimal.valueOf(value), Convert.Unit.ETHER).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    //转账ETH,指定price, limit
    public String transfer(String fileName, String password, String to, double value, BigInteger gasPrice, BigInteger gasLimit) {
        try {
            Credentials credentials = this.getCredentials(fileName, password);

            Transfer transfer = new Transfer(web3, new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)));
            TransactionReceipt receipt = transfer.sendFunds(to, BigDecimal.valueOf(value), Convert.Unit.ETHER, gasPrice, gasLimit).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊转账错误，错误原因：" + e.getMessage(), e);
        }
    }


    //--------------------------------------------------------------------------------------
    //根据秘钥内容操作钱包
    public String createWalletEx(String password) {
        File destinationDirectory = new File(keystore);
        try {
            String fileName = WalletUtils.generateNewWalletFile(password, destinationDirectory, true);
            if (fileName != null) {
                File file = new File(keystore + "/" + fileName);
                Long length = file.length();
                byte[] filecontent = new byte[length.intValue()];
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
                return new String(filecontent);
            }
            return null;
        } catch (Exception e) {
            throw new ServerException("创建以太坊钱包错误，错误原因：" + e.getMessage(), e);
        }
    }

    public String getCredentialsByFileName(String fileName) {
        try {
            if (fileName != null) {
                File file = new File(keystore + "/" + fileName);
                Long length = file.length();
                byte[] filecontent = new byte[length.intValue()];
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
                return new String(filecontent);
            }
            return null;
        } catch (Exception e) {
            throw new ServerException("获取以太坊钱包错误，错误原因：" + e.getMessage(), e);
        }
    }

    //以太坊智能合约获取余额
    public BigDecimal contractBalanceEx(String content, String password, String contractAddress) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);
            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    Contract.GAS_PRICE,
                    Contract.GAS_LIMIT);
            BigInteger balance = ethCoin.balanceOf(credentials.getAddress()).send();
            return Convert.fromWei(BigDecimal.valueOf(balance.doubleValue()), Convert.Unit.ETHER);
        } catch (Exception e) {
            throw new ServerException("以太坊代币获取余额错误，错误原因：" + e.getMessage(), e);
        }
    }

    // 以太坊只能合约转账
    public String contractTransferEx(String content, String password, String contractAddress, String to, double value) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);

            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    Contract.GAS_PRICE,
                    Contract.GAS_LIMIT);
            TransactionReceipt receipt = ethCoin.transfer(to, Convert.toWei(BigDecimal.valueOf(value), Convert.Unit.ETHER).toBigInteger()).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊智能合约转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    public int contractDecimalEx(String content, String password, String contractAddress) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);
            ETHCoin ethCoin = new ETHCoin(contractAddress, web3, credentials, new BigInteger("1000"), new BigInteger("10000"));
            return ethCoin.decimals().send().intValue();
        } catch (Exception e) {
            throw new ServerException("以太坊智能合约decimal错误，错误原因：" + e.getMessage(), e);
        }
    }

    // 以太坊只能合约转账
    public String contractTransferEx(String content, String password, String contractAddress, String to, double value, int decimal, BigInteger gasPrice) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);
            BigInteger tValue = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(Math.pow(10, decimal))).toBigInteger();
            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    gasPrice != null ? gasPrice : Contract.GAS_PRICE,
                    Contract.GAS_LIMIT);
            TransactionReceipt receipt = ethCoin.transfer(to, tValue).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊智能合约转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    // 以太坊只能合约转账指定price,limit
    public String contractTransferEx(String content, String password, String contractAddress, String to, double value, BigInteger gasPrice, BigInteger gasLimit) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);

            ETHCoin ethCoin = ETHCoin.load(contractAddress,
                    web3,
                    new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)),
                    gasPrice,
                    gasLimit);
            TransactionReceipt receipt = ethCoin.transfer(to, Convert.toWei(BigDecimal.valueOf(value), Convert.Unit.ETHER).toBigInteger()).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊智能合约转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    //获取ETH余额
    public BigDecimal getBalanceEx(String content, String password) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);
            EthGetBalance getBalance = web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            return Convert.fromWei(getBalance.getBalance().toString(), Convert.Unit.ETHER);
        } catch (Exception e) {
            throw new ServerException("获取以太坊余额错误，错误原因：" + e.getMessage(), e);
        }
    }

    //转账ETH
    public String transferEx(String content, String password, String to, double value) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);

            Transfer transfer = new Transfer(web3, new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)));
            TransactionReceipt receipt = transfer.sendFunds(to, BigDecimal.valueOf(value), Convert.Unit.ETHER).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊转账错误，错误原因：" + e.getMessage(), e);
        }
    }

    //转账ETH指定price,limit
    public String transferEx(String content, String password, String to, double value, BigInteger gasPrice, BigInteger gasLimit) {
        try {
            Credentials credentials = EthUtils.loadCredentialsByContect(password, content);

            Transfer transfer = new Transfer(web3, new FastRawTransactionManager(web3, credentials, new NoOpProcessor(web3)));
            TransactionReceipt receipt = transfer.sendFunds(to, BigDecimal.valueOf(value), Convert.Unit.ETHER, gasPrice, gasLimit).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new ServerException("以太坊转账错误，错误原因：" + e.getMessage(), e);
        }
    }


    //------------------------------------------------------------------------------------------------
    //判断地址是否有效
    public boolean isValidAddress(String address) {
        return WalletUtils.isValidAddress(address);
    }

    //获取交易
    public Transaction getTransaction(String transactionHash) {
        try {
            return web3.ethGetTransactionByHash(transactionHash).send().getResult();
        } catch (Exception e) {
            throw new ServerException("获取以太坊交易错误，错误原因：" + e.getMessage(), e);
        }
    }

    public TransactionReceipt getTransactionReceipt(String transactionHash) {
        try {
            Optional<TransactionReceipt> optional = web3.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt();
            return optional.orElse(null);
        } catch (Exception e) {
            throw new ServerException("获取以太坊交易错误，错误原因：" + e.getMessage(), e);
        }
    }

    public EthBlock.Block getLatestBlock() {
        try {
            return web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock();
        } catch (Exception e) {
            throw new ServerException("获取以太坊区块错误，错误原因：" + e.getMessage(), e);
        }
    }

    public EthBlock.Block getBlock(BigInteger blockNumber) {
        try {
            return web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send().getBlock();
        } catch (Exception e) {
            throw new ServerException("获取以太坊区块错误，错误原因：" + e.getMessage(), e);
        }
    }

    public BigInteger getBlockNumber() {
        try {
            return web3.ethBlockNumber().send().getBlockNumber();
        } catch (Exception e) {
            throw new ServerException("获取以太坊区块数，错误原因：" + e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {
        /*ETHHelper ethHelper = new ETHHelper("http://47.75.4.32:3331");
        ethHelper.keystore = "D:/keystore/eth";
        String password = "Jc_coin_etH_base";
        String fileName = ethHelper.createWallet(password);
        Credentials credentials = ethHelper.getCredentials(fileName, password);
        System.out.println("fileName:" + fileName + ", address:" + credentials.getAddress());*/

        ETHHelper ethHelper = new ETHHelper("http://47.75.4.32:3331", "eth");
        BigInteger num = ethHelper.getBlockNumber();

        EthBlock.Block blk = ethHelper.getBlock(BigInteger.valueOf(4000000));
        System.out.println(blk.getHash());

        List<EthBlock.TransactionResult> list = blk.getTransactions();
        for (int j = 0; j < list.size(); j++) {
            Transaction transaction = (Transaction) list.get(j);
            TransactionReceipt receipt = ethHelper.getTransactionReceipt(transaction.getHash());
            int tmp = 0;
        }
    }
}
