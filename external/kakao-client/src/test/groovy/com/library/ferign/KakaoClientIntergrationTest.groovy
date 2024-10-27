package com.library.ferign


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

import java.time.OffsetDateTime


@ActiveProfiles("test")
@SpringBootTest(classes = KakaoClientIntergrationTest.TestConfig.class)
class KakaoClientIntergrationTest extends Specification {

    @Autowired
    KakaoClient kakaoClient

    @EnableFeignClients(clients = KakaoClient.class)
    @EnableAutoConfiguration
    static class TestConfig {}


    def "kakao 호출"() {
        given:
        def title = "JAVA"
        def page = 1
        def size = 10

        when:
        def kakaoResponse = kakaoClient.search(title, page, size)

        then:
        verifyAll(kakaoResponse) {
            kakaoResponse != null
            kakaoResponse.documents().size() > 1
            kakaoResponse.meta() != null
        }
    }


}
