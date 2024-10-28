package com.library.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    EXTERNAL_API_ERROR("외부 API 호출 에러입니다."),
    UNKNOWN("알 수 없는 에러입니다"),
    INVALID_PARAMETER("유효하지 않은 요청값 입니다."),
    NO_RESOURCE("존재하지 않는 리소스입니다"),

    ;


    private final String description;
}
