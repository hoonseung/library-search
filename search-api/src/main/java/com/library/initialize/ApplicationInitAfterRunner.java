package com.library.initialize;

import com.library.entity.DailyStat;
import com.library.repository.DailyStatRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationInitAfterRunner implements CommandLineRunner {


    private final DailyStatRepository dailyStatRepository;

    @Override
    public void run(String... args) throws Exception {
        DailyStat stat1 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        DailyStat stat2 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        DailyStat stat3 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        DailyStat stat4 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        DailyStat stat5 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        DailyStat stat6 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        DailyStat stat7 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        DailyStat stat8 = DailyStat.createEntity("JPA", LocalDateTime.now());
        DailyStat stat9 = DailyStat.createEntity("JPA", LocalDateTime.now());
        DailyStat stat10 = DailyStat.createEntity("JPA", LocalDateTime.now());
        DailyStat stat11 = DailyStat.createEntity("Kotlin", LocalDateTime.now());
        DailyStat stat12 = DailyStat.createEntity("Kotlin", LocalDateTime.now());
        DailyStat stat13 = DailyStat.createEntity("Database", LocalDateTime.now());
        DailyStat stat14 = DailyStat.createEntity("Database", LocalDateTime.now());
        dailyStatRepository.saveAll(List.of(
            stat1, stat2, stat3, stat4, stat5, stat6, stat7, stat8, stat9, stat10, stat11, stat12,
            stat13, stat14
        ));
    }
}
