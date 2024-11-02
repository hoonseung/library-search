package com.library.service;

import com.library.controller.response.PageResultV2;
import com.library.controller.response.SearchResponse;
import com.library.controller.response.StatResponse;
import com.library.service.event.SearchEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookApplicationServiceV2 {

    private final BookElasticSearchService bookElasticSearchService;
    private final BookApplicationService bookApplicationService;
    private final ApplicationEventPublisher eventPublisher;

    public PageResultV2<SearchResponse> search(String title, int page, int size) {
        PageResultV2<SearchResponse> searchResponse = bookElasticSearchService.search(title, page,
            size);

        if (!searchResponse.contents().isEmpty()) {
            log.info("eventPublisher.publishEvent 호출 성공, contents.size: {}",
                searchResponse.contents().size());
            eventPublisher.publishEvent(new SearchEvent(title, LocalDateTime.now()));
        }

        return searchResponse;
    }

    public StatResponse findCountByQuery(String query, LocalDate date) {
        return bookApplicationService.findCountByQuery(query, date);
    }

    public List<StatResponse> findTop5DailyStat() {
        return bookApplicationService.findTop5DailyStat();
    }
}
