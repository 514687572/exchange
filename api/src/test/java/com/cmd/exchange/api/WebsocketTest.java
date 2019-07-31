package com.cmd.exchange.api;

import com.cmd.exchange.api.vo.CandleRequestVO;
import com.cmd.exchange.common.utils.DateUtil;
import com.cmd.exchange.common.utils.JSONUtil;
import com.cmd.exchange.common.vo.CandleResponse;
import okhttp3.*;
import okio.ByteString;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebsocketTest {

    private int connected = 0;
    private int errorCounts = 0;

    @Test
    public void getCandles() throws InterruptedException {
        String url = "ws://39.108.94.158:8088/ws/market";
        CountDownLatch c = new CountDownLatch(1);
        connect(c, url);
        Thread.sleep(100000);
    }


    @Test
    public void getCandlesPerf() throws InterruptedException {
        int threads = 400;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        //String url = "wss://coin.cn:443/ws/ws/market";
        String url = "wss://150.109.102.128:443/ws/ws/market";
        CountDownLatch c = new CountDownLatch(threads);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; ++i) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        connect(c, url);
                        //getCandles("39.108.94.158", "8088");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        c.await();
        long end = System.currentTimeMillis();
        System.out.println("elapsed time(seconds): " + (end - start) / 1000l);
        System.out.println("total connections: " + threads + " succeed: " + connected + " failed: " + errorCounts + "");
    }

    //通过原生websocket访问后台接口
    private void getCandlesByRawWebsocketClient(String ip, String port) throws InterruptedException {
        Dispatcher dispatcher = new Dispatcher();
        //默认只能开5个请求
        dispatcher.setMaxRequests(100);
        dispatcher.setMaxRequestsPerHost(100);
        OkHttpClient okClient = new OkHttpClient.Builder().dispatcher(dispatcher)
                .build();

        Request request = new Request.Builder()
                //服务器是SockJS协议，要求在endpint和websocket必须有2个路径，至于内容是什么无关紧要
                //实际用的时候把app_name和phone_number设置有意义的值
                .url("ws://" + ip + ":" + port + "/ws/market/app_name/" + 1 + "/websocket")
                // .url("ws://" + ip + ":" + port + "/market")
                .build();

        //建立连接 -- 每个连接对应线程， 所以无法测试10W连接的情况
        WebSocket ws = okClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                System.out.println("open");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("onMessage(text):" + text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                System.out.println("onMessage(byte)" + bytes.toString());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("onClosing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("onFailure");
            }
        });

        ws.send("[\"CONNECT\\naccept-version:1.1,1.0\\nheart-beat:10000,10000\\n\\n\\u0000\"]");
        heartBeat(ws);

        ws.send("[\"SUBSCRIBE\\nid:sub-0\\ndestination:/ws/market/candles?coinName=btc&settlementCurrency=znb\\n\\n\\u0000\"]");
        ws.send("[\"SUBSCRIBE\\nid:sub-0\\ndestination:/ws/market/time-series?coinName=btc&settlementCurrency=znb\\n\\n\\u0000\"]");
        ws.send("[\"SUBSCRIBE\\nid:sub-0\\ndestination:/ws/market/open-trade-list?coinName=btc&settlementCurrency=znb\\n\\n\\u0000\"]");
        ws.send("[\"SUBSCRIBE\\nid:sub-0\\ndestination:/ws/market/trade-log-list?coinName=btc&settlementCurrency=znb\\n\\n\\u0000\"]");


    }

    //通过stomp 客户端连接
    private void connect(CountDownLatch c, String url) throws InterruptedException {

        WebSocketClient transport = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        MappingJackson2MessageConverter stringMessageConverter = new MappingJackson2MessageConverter();

        stompClient.setMessageConverter(stringMessageConverter);


        final ListenableFuture<StompSession> future = stompClient.connect(url, new StompSessionHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return "".getClass();
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("handleFrame" + o);
            }

            @Override
            public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                ++connected;
                System.out.println("afterConnected: " + connected);

                c.countDown();
            }

            @Override
            public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                System.out.println("handleException");
                ++errorCounts;
                throwable.printStackTrace();
            }

            @Override
            public void handleTransportError(StompSession stompSession, Throwable throwable) {
                ++errorCounts;
                System.out.println("handleTransportError: " + errorCounts);
                c.countDown();
            }
        });

    }

    private void getCandles(StompSession stompSession) {
        stompSession.subscribe("/ws/market/candles?symbol=btc/znb&resolution=1&type=kline", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return CandleResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                //  System.out.println("handleFrame - /ws/market/candles?symbol=btc/znb&resolution=1&type=kline" + o);
            }
        });

        String nonce = "66666666666666";
        stompSession.subscribe("/ws/market/candles?symbol=btc/znb&resolution=1&type=kline&nonce=" + nonce, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return CandleResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                // System.out.println("handleFrame - send" + o);
            }
        });

        String destination = "/ws/market/candles/" + nonce;
        CandleRequestVO request = new CandleRequestVO();
        Date endTime = new Date();
        request.setFrom(DateUtils.addMinutes(endTime, -30).getTime() / 1000l);
        request.setTo(endTime.getTime() / 1000l);
        request.setResolution("1").setSymbol("btc/znb").setType("kline");

        stompSession.send(destination, JSONUtil.toJSONString(request));
    }


    private void heartBeat(WebSocket ws) {
        TimerTask heartBeatTask = new TimerTask() {
            @Override
            public void run() {
                ws.send("[\"CONNECT\\naccept-version:1.1,1.0\\nheart-beat:10000,10000\\n\\n\\u0000\"]");
            }
        };

        Timer timer = new Timer();
        timer.schedule(heartBeatTask, 1000);
    }
}
