package com.library.service


import com.library.entity.DailyStat
import spock.lang.Specification

import java.time.LocalDate

class BookApplicationServiceTest extends Specification {

    BookQueryService bookQueryService = Mock()
    DailyStatCommandService dailyStatCommandService = Mock()
    DailyStatQueryService dailyStatQueryService = Mock()
    BookApplicationService bookApplicationService

    void setup() {
        bookApplicationService = new BookApplicationService(bookQueryService, dailyStatCommandService, dailyStatQueryService)
    }

    def "searh 메서드 호출 시 외부 api 호출과 통계 데이터를 DB에 저장한다."() {
        given:
        def givenQuery = "HTTP"
        def givenPage = 1
        def givenSize = 10

        when:
        bookApplicationService.search(givenQuery, givenPage, givenSize)

        then:
        1 * bookQueryService.search(givenQuery, givenPage, givenSize) >> {
            String query, int page, int size ->
                {
                    assert query == givenQuery
                    assert page == givenPage
                    assert size == givenSize
                }
        }

        and:
        1 * dailyStatCommandService.save(*_) >> {
            DailyStat dailyStat -> assert dailyStat.query == givenQuery
        }
    }

    def "findCountByQuery 호출 시 인자를 그대로 넘긴다."() {
        given:
        def givenQuery = "HTTP"
        def givenDate = LocalDate.of(2024, 5, 1)

        when:
        bookApplicationService.findCountByQuery(givenQuery, givenDate)

        then:
        1 * dailyStatQueryService.findQueryCount(*_) >> {
            String query, LocalDate date ->
                assert query == givenQuery
                assert date == givenDate
        }
    }
}
