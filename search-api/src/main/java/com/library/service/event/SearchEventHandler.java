package com.library.service.event;

import com.library.entity.DailyStat;
import com.library.service.DailyStatCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEventHandler {

    private final DailyStatCommandService dailyStatCommandService;

    @Async
    @EventListener
    public void eventHandle(SearchEvent searchEvent) {
        log.info("eventHandle 호출 성공, searchEvent: {}", searchEvent);
        log.info("current Thread in [SearchEventHandler] eventHandle: {}", Thread.currentThread().getName());
        dailyStatCommandService.save(DailyStat.createEntity(
            searchEvent.query(),
            searchEvent.eventDateTime())
        );
    }


}
