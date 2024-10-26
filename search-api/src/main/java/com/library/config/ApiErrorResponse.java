package com.library.config;

import com.library.ErrorType;

public record ApiErrorResponse(
    String errorMessage,
    ErrorType errorType) {

}
