package com.library.service


import com.library.repository.DailyStatRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class DailyStatQueryServiceTest extends Specification {

    DailyStatRepository dailyStatRepository = Mock()
    DailyStatQueryService dailyStatQueryService

    void setup() {
        dailyStatQueryService = new DailyStatQueryService(dailyStatRepository)
    }

    def "findQueryCount 조회 시 하루치를 조회하면서 쿼리 개수가 반환된다."() {
        given:
        def givenQuery = "HTTP"
        def givenDate = LocalDate.of(2024, 5, 1)

        and: "2024-05-01이 들어왔을 때 2024-05-01 00시 부터 2024-05-01 24시까지의 query 개수 조회 후 2개를 반환"
        1 * dailyStatRepository.countByQueryAndEventDateTimeBetween(
                givenQuery,
                givenDate.atStartOfDay(),
                givenDate.atTime(LocalTime.MAX)) >> 2L

        when:
        def response = dailyStatQueryService.findQueryCount(givenQuery, givenDate)

        then: "2개를 반환하는지 검증"
        verifyAll(response) {
            assert response.query() == givenQuery
            assert response.count() == 2L
        }
    }
}
