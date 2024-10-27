package com.library.repository;

import com.library.entity.DailyStat;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {

    long countByQueryAndEventDateTimeBetween(String query, LocalDateTime start, LocalDateTime end);
}

