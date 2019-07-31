package com.cmd.exchange.common;

import com.cmd.exchange.blockchain.eth.ETHCoin;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class EthTest {

    //@Test
    public void testDecimal() throws Exception {
        Web3j web3 = Web3j.build(new HttpService("http://" + "127.0.0.1" + ":" + 8845));
        Credentials credentials = Credentials.create("d3e9d64f6c244156b77c58bb4dc7a766006453ab6d470175c9b952edf19d11bc");
        ETHCoin ethCoin = new ETHCoin("0x7a9a5f81d4626b6a7213b2d51cc926e7a93d7094", web3, credentials,
                new BigInteger("1000"), new BigInteger("10000"));
        System.out.println("decimals=" + ethCoin.decimals().send().intValue());
    }
}
