package com.library.controller;

import com.library.controller.request.SearchRequest;
import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.service.BookApplicationService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookApplicationService bookApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@ModelAttribute @Valid SearchRequest searchRequest) {
        log.info("[BookController] searchRequest={}", searchRequest);
        return bookApplicationService.search(
            searchRequest.getQuery(),
            searchRequest.getPage(),
            searchRequest.getSize()
        );
    }

    @GetMapping("/stats")
    public StatResponse findCountByQuery(
        @RequestParam(name = "query") String query,
        @RequestParam(name = "date") LocalDate date) {
        log.info("[BookController] find stats query={}, date={}", query, date);
        return bookApplicationService.findCountByQuery(query, date);
    }

    @GetMapping("/stats/ranking")
    public List<StatResponse> findTopDailyStat(){
        log.info("[BookController] find top 5 stats");
        return bookApplicationService.findTop5DailyStat();
    }
}
