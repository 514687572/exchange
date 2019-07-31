package com.cmd.exchange.common.enums;

public enum TransactionPairs {
    ETHUSDT("ethusdt"),
    BTCUSDT("btcusdt"),
    EOSUSDT("eosusdt"),
    LTCUSDT("ltcusdt"),
    BCHUSDT("bchusdt"),
    XRPUSDT("xrpusdt");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    TransactionPairs(String value) {
        this.value = value;
    }


}
