package com.cmd.exchange.admin;

import com.cmd.exchange.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        //$5$brZ2fFx5K7MQ6ZE/$xv2k3xjmeUbLMnYmqiSwl12aGFL5P9TnMo8gQ00bFP5
        //$5$brZ2fFx5K7MQ6ZE/$xv2k3xjmeUbLMnYmqiSwl12aGFL5P9TnMo8gQ00bFP5
        //$5$brZ2fFx5K7MQ6ZE/
        userService.resetPayPassword(14, "123456");
    }

}
