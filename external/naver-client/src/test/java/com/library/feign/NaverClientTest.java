package com.library.feign;

import com.library.feign.NaverClientTest.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @EnableFeignClients(clients = NaverClient.class)
    @EnableAutoConfiguration
    static class TestConfig {

    }

    @Test
    void callNaver() {
        String result = naverClient.search("HTTP", 1, 5);

        Assertions.assertNotNull(result);
        System.out.println(result);
    }

}