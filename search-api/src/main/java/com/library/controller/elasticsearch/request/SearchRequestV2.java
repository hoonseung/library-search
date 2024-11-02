package com.library.controller.elasticsearch.request;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "검색 요청 모델 V2")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchRequestV2 {

    @Schema(description = "검색 쿼리", example = "HTTP", requiredMode = RequiredMode.REQUIRED, maxLength = 50)
    @Size(max = 50, message = "query 값은 최대 50자를 초과할 수 없습니다")
    @NotBlank(message = "입력은 필수 입니다.")
    private String query;

    @Schema(description = "검색 페이지", example = "0", requiredMode = RequiredMode.REQUIRED, minLength = 0, maxLength = 10_000)
    @Min(value = 0, message = "페이지번호는 0이상이어야 합니다.")
    @Max(value = 10_000, message = "페이지번호는 50이하이어야 합니다.")
    @NotNull(message = "페이지 번호는 필수입니다")
    private Integer page;

    @Schema(description = "검색 사이즈", example = "10", requiredMode = RequiredMode.REQUIRED, minLength = 1, maxLength = 50)
    @Min(value = 1, message = "페이지크기는 1이상이어야 합니다.")
    @Max(value = 50, message = "페이지크기는 50이하이어야 합니다.")
    @NotNull(message = "페이지 사이즈는 필수입니다")
    private Integer size;
}


