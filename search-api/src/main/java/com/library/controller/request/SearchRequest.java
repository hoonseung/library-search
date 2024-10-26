package com.library.controller.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {

    @Size(max = 50, message = "query 값은 최대 50자를 초과할 수 없습니다")
    @NotBlank(message = "입력은 필수 입니다.")
    private String query;

    @Min(value = 1, message = "페이지번호는 1이상이어야 합니다.")
    @Max(value = 10_000, message = "페이지번호는 50이하이어야 합니다.")
    @NotNull(message = "페이지 번호는 필수입니다")
    private Integer page;

    @Min(value = 1, message = "페이지번호는 1이상이어야 합니다.")
    @Max(value = 50, message = "페이지번호는 50이하이어야 합니다.")
    @NotNull(message = "페이지 사이즈는 필수입니다")
    private Integer size;
}


