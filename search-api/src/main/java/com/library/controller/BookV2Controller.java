package com.library.controller;

import com.library.controller.elasticsearch.request.SearchRequestV2;
import com.library.controller.response.ApiErrorResponse;
import com.library.controller.response.PageResultV2;
import com.library.controller.response.SearchResponse;
import com.library.service.BookElasticSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v2/books")
@RestController
@Tag(name = "book V2", description = "book API V2")
public class BookV2Controller {

    private final BookElasticSearchService bookElasticSearchService;


    @Operation(summary = "search API V2", description = "도서 검색결과 제공")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResultV2.class))),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public PageResultV2<SearchResponse> search(
        @ModelAttribute @Valid SearchRequestV2 searchRequestV2) {
        log.info("[BookV2Controller] searchRequestV2={}", searchRequestV2);
        return bookElasticSearchService.search(
            searchRequestV2.getQuery(),
            searchRequestV2.getPage(),
            searchRequestV2.getSize());
    }
}
