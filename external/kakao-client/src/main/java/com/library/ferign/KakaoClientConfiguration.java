package com.library.ferign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoClientConfiguration {

    private static final String HEADER_PREFIX = "KakaoAK ";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public RequestInterceptor kakaoRequestInterceptor(
        @Value("${external.kakao.headers.rest-api-key}") String restApiKey) {
        return requestTemplate -> requestTemplate.header("Authorization", HEADER_PREFIX + restApiKey);
    }

    @Bean
    public ErrorDecoder kakaoErrorDecoder(){
        return new KakaoErrorDecoder(objectMapper);
    }
}
