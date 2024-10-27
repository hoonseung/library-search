package com.library.ferign

import feign.RequestTemplate
import spock.lang.Specification

class KakaoClientConfigurationTest extends Specification {

    KakaoClientConfiguration configuration

    void setup() {
        configuration = new KakaoClientConfiguration()
    }

    def "requestInterceptor의 헤더에 kakao rest api key값이 잘 적용되는지"() {
        given:
        def template = new RequestTemplate()
        def clientSecret = "secret"

        and: "interceptor를 타기전에 header는 존재하지 않는다."
        template.headers()["Authorization"] == null

        when:
        def interceptor = configuration.kakaoRequestInterceptor(clientSecret)
        interceptor.apply(template)


        then: "interceptor 거치면 header가 추가된다."
        template.headers()["Authorization"].contains("KakaoAK " + clientSecret)
    }
}
