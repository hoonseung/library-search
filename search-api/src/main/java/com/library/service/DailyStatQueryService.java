package com.library.service;

import com.library.controller.response.StatResponse;
import com.library.repository.DailyStatRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DailyStatQueryService {

    private final DailyStatRepository dailyStatRepository;

    public StatResponse findQueryCount(String query, LocalDate localDate) {
        long count = dailyStatRepository.countByQueryAndEventDateTimeBetween(
            query,
            localDate.atStartOfDay(),
            localDate.atTime(LocalTime.MAX)
        );

        return new StatResponse(query, count);
    }

    public List<StatResponse> findTop5DailyStat() {
        return dailyStatRepository.findTopDailyStat(PageRequest.of(0, 5));
    }
}
