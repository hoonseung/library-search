package com.library.config;

import static com.library.ErrorType.INVALID_PARAMETER;
import static com.library.ErrorType.UNKNOWN;

import com.library.ApiException;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e) {
        log.error("Api Exception occurs message={}, className={},"
            , e.getErrorMessage(), e.getClass().getSimpleName());
        return ResponseEntity
            .status(e.getHttpStatus())
            .header("Content-Type", "application/json; utf-8")
            .body(new ApiErrorResponse(e.getErrorMessage(), e.getErrorType()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("Exception occurs message={}, className={},"
            , e.getMessage(), e.getClass().getSimpleName());
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiErrorResponse(UNKNOWN.getDescription(), UNKNOWN));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException occurs message={}, className={}",
            e.getMessage(), e.getClass().getSimpleName());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiErrorResponse(createMessage(e.getBindingResult()), INVALID_PARAMETER));
    }

    private String createMessage(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        if (Objects.nonNull(fieldError) && StringUtils.hasText(fieldError.getDefaultMessage())) {
            return fieldError.getDefaultMessage();
        }

        return bindingResult.getFieldErrors()
            .stream()
            .map(FieldError::getField)
            .collect(Collectors.joining(", ")) + "의값이 정확하지 않습니다";
    }

}
