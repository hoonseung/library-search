package com.library.ferign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.ApiException;
import com.library.ErrorType;
import com.library.KakaoErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class KakaoErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public KakaoErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            KakaoErrorResponse errorResponse = objectMapper.readValue(
                response.body().asInputStream(), KakaoErrorResponse.class);
            throw new ApiException(errorResponse.message(), ErrorType.EXTERNAL_API_ERROR, HttpStatus.valueOf(response.status()));
        } catch (IOException e){
            log.error("{} 에러 메세지 파싱 에러 code={}, request={}, methodKey={}, errorMessage={}",
                "KAKAO", response.status(), response.request(), methodKey, e.getMessage());
            throw new ApiException("카카오 메세지 파싱에러", ErrorType.EXTERNAL_API_ERROR,
                HttpStatus.valueOf(response.status()));
        }
    }
}
