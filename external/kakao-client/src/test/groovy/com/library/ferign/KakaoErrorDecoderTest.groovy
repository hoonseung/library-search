package com.library.ferign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.exception.ApiException
import com.library.exception.ErrorType
import com.library.KakaoErrorResponse
import feign.Request
import feign.Response
import org.springframework.http.HttpStatus
import spock.lang.Specification

class KakaoErrorDecoderTest extends Specification {

    ObjectMapper objectMapper = Mock()
    InputStream inputStream = Mock()
    KakaoErrorDecoder errorDecoder = new KakaoErrorDecoder(objectMapper)

    def "ErrorDecoder에서 에러발생 시 RuntimeException 예외가 에러 메세지와 함께 반환된다."() {
        given:
        def responseBody = Mock(Response.Body)
        def response = Response.builder()
                .status(400)
                .request(Request.create(Request.HttpMethod.GET, "testUrl", [:], null as Request.Body, null))
                .body(responseBody)
                .build()

        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new KakaoErrorResponse("InvalidArgument!!", "size is more than max")
        when:
        errorDecoder.decode(_ as String, response)

        then:
        ApiException e = thrown()
        verifyAll {
            e.errorMessage == "size is more than max"
            e.httpStatus == HttpStatus.BAD_REQUEST
            e.errorType == ErrorType.EXTERNAL_API_ERROR
        }
    }
}
