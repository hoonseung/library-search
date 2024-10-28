package com.library.service;

import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.service.event.SearchEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookApplicationService {

    private final BookQueryService bookQueryService;
    private final DailyStatQueryService dailyStatQueryService;
    private final ApplicationEventPublisher eventPublisher;


    public PageResult<SearchResponse> search(String query, int page, int size) {
        PageResult<SearchResponse> response = bookQueryService.search(query, page, size);
        log.info("bookQueryService 호출 성공");

        if (!response.contents().isEmpty()) {
            log.info("eventPublisher.publishEvent 호출 성공, contents.size: {}",
                response.contents().size());
            eventPublisher.publishEvent(new SearchEvent(query, LocalDateTime.now()));
        }
        log.info("current Thread in [BookApplicationService] search: {}", Thread.currentThread().getName());
        return response;
    }


    public StatResponse findCountByQuery(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCount(query, date);
    }

    public List<StatResponse> findTop5DailyStat() {
        return dailyStatQueryService.findTop5DailyStat();
    }


}
