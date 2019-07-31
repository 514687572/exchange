/*
 * Bitcoin-JSON-RPC-Client License
 *
 * Copyright (c) 2013, Mikhail Yevchenko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.cmd.exchange.blockchain.bitcoin;

/**
 * @author Mikhail Yevchenko m.ṥῥẚɱ.ѓѐḿởύḙ@azazar.com, Alessandro Polverini
 * <alex@polverini.org>
 */
public class BitcoinRException extends BitcoinRpcException {

    private String rpcMethod;
    private String rpcParams;
    private int responseCode;
    private String responseMessage;
    private String response;

    /**
     * Creates a new instance of <code>BitcoinRPCException</code> with response
     * detail.
     *
     * @param method          the rpc method called
     * @param params          the parameters sent
     * @param responseCode    the HTTP code received
     * @param responseMessage the HTTP response message
     * @param response        the error stream received
     */
    public BitcoinRException(String method, String params, int responseCode, String responseMessage, String response) {
        super("RPC Query Failed (method: " + method + ", params: " + params + ", response code: " + responseCode + " responseMessage " + responseMessage + ", response: " + response);
        this.rpcMethod = method;
        this.rpcParams = params;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.response = response;
    }

    public BitcoinRException(String method, String params, Throwable cause) {
        super("RPC Query Failed (method: " + method + ", params: " + params + ")", cause);
        this.rpcMethod = method;
        this.rpcParams = params;
    }

    /**
     * Constructs an instance of <code>BitcoinRPCException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BitcoinRException(String msg) {
        super(msg);
    }

    public BitcoinRException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getRpcMethod() {
        return rpcMethod;
    }

    public String getRpcParams() {
        return rpcParams;
    }

}
