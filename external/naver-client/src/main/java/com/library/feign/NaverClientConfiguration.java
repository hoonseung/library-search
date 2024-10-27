package com.library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverClientConfiguration {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public RequestInterceptor naverRequestInterceptor(
        @Value("${external.naver.headers.client-id}") String clientId,
        @Value("${external.naver.headers.client-secret}") String clientSecret) {

        return requestTemplate -> requestTemplate
            .header("X-Naver-Client-Id", clientId)
            .header("X-Naver-Client-Secret", clientSecret);
    }

    @Bean
    public NaverErrorDecoder naverErrorDecoder() {
        return new NaverErrorDecoder(objectMapper);
    }
}
