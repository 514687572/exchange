package com.cmd.exchange.blockchain.usdt;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cmd.exchange.blockchain.bitcoin.BitcoinJSONRPCClient;


public class OmniClient extends BitcoinJSONRPCClient {

    private int propertyid = 31;

    public OmniClient(String rpcUrl, int propertyid) throws Exception {
        super(rpcUrl);
        this.propertyid = propertyid;
    }

    public String omni_getAccountAddress(String account) {
        List<String> list = getAddressesByAccount(account);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return getAccountAddress(account);
        }
    }

    public double omni_getBalance(String address) {
        Map<String, Object> m = (Map) query("omni_getbalance", address, propertyid);
        return new BigDecimal(m.get("balance").toString()).doubleValue();
    }

    public String omni_send(String fromAddress, String toAddress, double amount) {
        return (String) query("omni_send", fromAddress, toAddress, propertyid, String.valueOf(amount));
    }
    public String omni_funded_send(String fromAddress, String toAddress, double amount,String feeaddress){
        return (String)query("omni_funded_send", fromAddress, toAddress, propertyid, String.valueOf(amount),feeaddress);
    }

    public int omni_getBlockCount() {
        return getBlockCount();
    }

    public List<String> omni_listBlockTransactions(int block) {
        return (List<String>) query("omni_listblocktransactions", block);
    }

    public Map<String, Object> omni_getTransaction(String txid) {
        return (Map) query("omni_gettransaction", txid);
    }

    public static void main(String[] argv) throws Exception {
        String username = "wqt_user";
        String password = "wqt_pass";
        String host = "13.56.183.146";
        String port = "18335";
        String url = "http://" + username + ":" + password + "@" + host + ":" + port + "/";

        OmniClient client = new OmniClient(url, 31);
        String txid = client.omni_send("16E7YiryZPo14dhfonTv4MX3h121ownP4t", "15RssCC7tfPUHqYBAKMrowr34zawS3CVaA", 1);
        double tmp = client.omni_getBalance("15RssCC7tfPUHqYBAKMrowr34zawS3CVaA");
        int t = 90;
    }
}
