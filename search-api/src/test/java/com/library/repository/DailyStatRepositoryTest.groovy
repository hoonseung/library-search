package com.library.repository

import com.library.entity.DailyStat
import com.library.feign.NaverClient
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime

@ActiveProfiles("test")
@DataJpaTest
class DailyStatRepositoryTest extends Specification {

    @Autowired
    DailyStatRepository dailyStatRepository

    @SpringBean
    NaverClient naverClient = Mock()

    def "저장 후 조회한다."() {
        given:
        def query = "HTTP"
        def eventDateTime = LocalDateTime.of(2024, 10, 10, 1, 1, 1)

        when: "바로 em.persist 후 id 식별자 값을 조회한다"
        def entityPs = dailyStatRepository.save(DailyStat.createEntity(query, eventDateTime))

        then: "실제 저장이 된다"
        verifyAll(entityPs) {
            entityPs.getId() == 1L
            entityPs.getQuery() == query
            entityPs.getEventDateTime() == eventDateTime
        }

    }


    def "일별 query 키운트를 조회한다."() {
        given:
        def givenQuery = "HTTP"
        def givenNow = LocalDateTime.of(2024, 9, 1, 0, 0, 0)
        def stat1 = DailyStat.createEntity(givenQuery, givenNow.plusMinutes(10))

        def stat2 = DailyStat.createEntity(givenQuery, givenNow.minusMinutes(10))
        def stat3 = DailyStat.createEntity(givenQuery, givenNow.plusMinutes(10))

        def stat4 = DailyStat.createEntity("java", givenNow.plusMinutes(10))
        dailyStatRepository.saveAll([stat1, stat2, stat3, stat4])
        when:
        def result = dailyStatRepository.countByQueryAndEventDateTimeBetween(givenQuery, givenNow, givenNow.plusDays(1))

        then:
        verifyAll(result) {
            assert result == 2
        }
    }


    def "가장 많이 검색된 쿼리 상위 3개를 개수와 함께 반환한다."() {
        given:
        def stat1 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        def stat2 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        def stat3 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        def stat4 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        def stat5 = DailyStat.createEntity("HTTP", LocalDateTime.now());
        def stat6 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        def stat7 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        def stat8 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        def stat9 = DailyStat.createEntity("JAVA", LocalDateTime.now());
        def stat10 = DailyStat.createEntity("JPA", LocalDateTime.now());
        def stat11 = DailyStat.createEntity("Kotlin", LocalDateTime.now());
        def stat12 = DailyStat.createEntity("Database", LocalDateTime.now());
        def stat13 = DailyStat.createEntity("Database", LocalDateTime.now());
        def stat14 = DailyStat.createEntity("Database", LocalDateTime.now());

        dailyStatRepository.saveAll([
                stat1, stat2, stat3, stat4, stat5, stat6, stat7, stat8, stat9, stat10, stat11, stat12, stat13, stat14
        ])

        when:
        def pageRequest = PageRequest.of(0, 3)
        def states = getDailyStatRepository().findTopDailyStat(pageRequest)

        then:
        verifyAll(states) {
            assert states.size() == 3
            assert states.get(0).query() == "HTTP"
            assert states.get(1).query() == "JAVA"
            assert states.get(2).query() == "Database"
        }
    }
}
