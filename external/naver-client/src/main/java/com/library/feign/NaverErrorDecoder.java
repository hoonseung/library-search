package com.library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.NaverErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import org.springframework.web.ErrorResponse;


public class NaverErrorDecoder implements ErrorDecoder {

    private ObjectMapper objectMapper;

    public NaverErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String s, Response response) {
        try {
            NaverErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(),
                NaverErrorResponse.class);
            throw new RuntimeException(errorResponse.errorMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
