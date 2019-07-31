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
 * @author Mikhail Yevchenko m.ṥῥẚɱ.ѓѐḿởύḙ@azazar.com
 */
public class BitcoinUtil {

    public static double normalizeAmount(double amount) {
        return (long) (0.5d + (amount / 0.00000001)) * 0.00000001;
    }

//    public static void main(String[] args) {
//        NumberFormat f = new DecimalFormat("#.#########");
//        System.out.println(f.format(normalizeAmount(1d)) + ":\n1");
//        System.out.println(f.format(normalizeAmount(0.00000001d)) + ":\n0.00000001");
//        System.out.println(f.format(normalizeAmount(0.000000001d)) + ":\n0");
//        System.out.println(f.format(normalizeAmount(0.000000006d)) + ":\n0.00000001");
//        System.out.println(f.format(normalizeAmount(22123123.12312312d)) + ":\n22123123.12312312");
//    }

}
