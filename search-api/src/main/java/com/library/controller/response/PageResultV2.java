package com.library.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "페이징 결과 모델 V2")
public record PageResultV2<T>(
    @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
    int page,
    @Schema(description = "현재 페이지 크기", example = "10")
    int size,
    @Schema(description = "본문")
    List<T> contents) {

}
