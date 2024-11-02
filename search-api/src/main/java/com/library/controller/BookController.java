package com.library.controller;

import com.library.controller.request.SearchRequest;
import com.library.controller.response.ApiErrorResponse;
import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.service.BookApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/books")
@Tag(name = "book V1", description = "book API V1")
public class BookController {

    private final BookApplicationService bookApplicationService;

    @Operation(summary = "search API", description = "도서 검색결과 제공")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResult.class))),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public PageResult<SearchResponse> search(@ModelAttribute @Valid SearchRequest searchRequest) {
        log.info("[BookController] searchRequest={}", searchRequest);
        return bookApplicationService.search(
            searchRequest.getQuery(),
            searchRequest.getPage(),
            searchRequest.getSize()
        );
    }

    @Operation(summary = "stats API", description = "도서 검색어 통계 결과 제공")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatResponse.class))),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/stats")
    public StatResponse findCountByQuery(
        @RequestParam(name = "query") String query,
        @RequestParam(name = "date") LocalDate date) {
        log.info("[BookController] find stats query={}, date={}", query, date);
        return bookApplicationService.findCountByQuery(query, date);
    }

    @Operation(summary = "stats ranking API", description = "도서 상위 검색어 통계 결과 제공")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatResponse.class)))),
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/stats/ranking")
    public List<StatResponse> findTopDailyStat() {
        log.info("[BookController] find top 5 stats");
        return bookApplicationService.findTop5DailyStat();
    }
}
