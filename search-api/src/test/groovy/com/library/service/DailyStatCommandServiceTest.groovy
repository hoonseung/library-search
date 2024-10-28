package com.library.service

import com.library.entity.DailyStat
import com.library.repository.DailyStatRepository
import spock.lang.Specification

import java.time.LocalDateTime

class DailyStatCommandServiceTest extends Specification {


    DailyStatRepository dailyStatRepository = Mock()
    DailyStatCommandService dailyStatCommandService;


    void setup() {
        dailyStatCommandService = new DailyStatCommandService(dailyStatRepository)
    }

    def "저장 시 넘어온 인자 그대로 호출된다."() {
        given:
        def entity = DailyStat.createEntity("HTTP", LocalDateTime.now())

        when:
        dailyStatCommandService.save(entity)

        then:
        1 * dailyStatRepository.save(*_) >> {
            DailyStat dailyStat ->
                {
                    assert dailyStat.query == dailyStat.query
                    assert dailyStat.eventDateTime == dailyStat.eventDateTime
                }
        }
    }
}
