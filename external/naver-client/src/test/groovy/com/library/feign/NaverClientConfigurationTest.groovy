package com.library.feign


import feign.RequestTemplate
import spock.lang.Specification

class NaverClientConfigurationTest extends Specification {

    NaverClientConfiguration configuration

    void setup() {
        configuration = new NaverClientConfiguration()
    }

    def "requestInterceptor의 헤더에 naver key와 secret값이 잘 적용되는지"() {
        given:
        def template = new RequestTemplate()
        def clientId = "id"
        def clientSecret = "secret"

        and: "interceptor를 타기전에 header는 존재하지 않는다."
        template.headers()["X-Naver-Client-Id"] == null
        template.headers()["X-Naver-Client-Secret"] == null

        when:
        def interceptor = configuration.requestInterceptor(clientId, clientSecret)
        interceptor.apply(template)


        then: "interceptor 거치면 header가 추가된다."
        template.headers()["X-Naver-Client-Id"].contains(clientId)
        template.headers()["X-Naver-Client-Secret"].contains(clientSecret)
    }
}