package com.library.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.NaverErrorResponse
import feign.Request
import feign.Response
import spock.lang.Specification


class NaverErrorDecoderTest extends Specification {

    ObjectMapper objectMapper = Mock()
    InputStream inputStream = Mock()
    NaverErrorDecoder errorDecoder = new NaverErrorDecoder(objectMapper)

    def "ErrorDecoder에서 에러발생 시 RuntimeException 예외가 에러 메세지와 함께 반환된다."() {
        given:
        def responseBody = Mock(Response.Body)
        def response = Response.builder()
                .status(400)
                .request(Request.create(Request.HttpMethod.GET, "testUrl", [:], null as Request.Body, null))
                .body(responseBody)
                .build()

        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new NaverErrorResponse("error!!", "SE03")
        when:
        errorDecoder.decode(_ as String, response)

        then:
        RuntimeException e = thrown()
        e.message == "error!!"
    }
}