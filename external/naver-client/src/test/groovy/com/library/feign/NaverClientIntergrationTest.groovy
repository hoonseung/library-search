package com.library.feign


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@ActiveProfiles("test")
@SpringBootTest(classes = NaverClientIntergrationTest.TestConfig.class)
class NaverClientIntergrationTest extends Specification {

    @Autowired
    NaverClient naverClient;

    @EnableFeignClients(clients = NaverClient.class)
    @EnableAutoConfiguration
    static class TestConfig {

    }

    def "naver api 호출"() {
        given:

        when:
        def response = naverClient.search("HTTP", 1, 10)

        then:
        print response
    }
}