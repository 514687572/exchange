package com.cmd.exchange.api;

import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


public class PerfTest {
    @Autowired
    UserService userService;

    RestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void login() {
        String phone = "test-0";
        String password = "a123456";
        ResponseEntity<CommonResponse> response = restTemplate.postForEntity("http://39.108.94.158:8088/user/login?phone=" + phone + "&password=" + password, null, CommonResponse.class);

        if (response.getBody().getStatusCode() != 0) {

        }


        //userService.login("86","test-0","a123456","111","1.2.2.2");
    }
}
