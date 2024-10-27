package com.library.service;

import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.entity.DailyStat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookApplicationService {

    private final BookQueryService bookQueryService;
    private final DailyStatCommandService dailyStatCommandService;
    private final DailyStatQueryService dailyStatQueryService;


    public PageResult<SearchResponse> search(String query, int page, int size) {
        PageResult<SearchResponse> response = bookQueryService.search(query, page, size);
        log.info("bookQueryService 호출 성공");
        dailyStatCommandService.save(DailyStat.createEntity(query, LocalDateTime.now()));
        return response;
    }

    public StatResponse findCountByQuery(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCount(query, date);
    }

    public List<StatResponse> findTop5DailyStat() {
        return dailyStatQueryService.findTop5DailyStat();
    }


}
