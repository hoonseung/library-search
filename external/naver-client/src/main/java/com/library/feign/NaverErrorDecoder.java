package com.library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.exception.ApiException;
import com.library.exception.ErrorType;
import com.library.NaverErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class NaverErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public NaverErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            NaverErrorResponse errorResponse = objectMapper.readValue(
                response.body().asInputStream(),
                NaverErrorResponse.class);
            throw new ApiException(
                errorResponse.errorMessage(),
                ErrorType.EXTERNAL_API_ERROR,
                HttpStatus.valueOf(response.status()));
        } catch (IOException e) {
            log.error("{} 에러 메세지 파싱 에러 code={}, request={}, methodKey={}, errorMessage={}",
                "NAVER", response.status(), response.request(), methodKey, e.getMessage());
            throw new ApiException("네이버 메세지 파싱에러", ErrorType.EXTERNAL_API_ERROR,
                HttpStatus.valueOf(response.status()));
        }
    }
}
