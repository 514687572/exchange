package com.cmd.exchange.external.utils;

import com.cmd.exchange.external.request.CommonRequest;

public class RequestSignVerifier {

    public static boolean verify(String apiSecret, CommonRequest request) {
        return request.isValidSign(apiSecret);
    }

}
