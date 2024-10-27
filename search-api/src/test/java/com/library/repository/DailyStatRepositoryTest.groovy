package com.library.repository

import com.library.entity.DailyStat
import com.library.entity.DailyStatTest
import com.library.feign.NaverClient
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback
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

    @Rollback(false)
    def "저장 후 조회한다."(){
        given:
        def query = "HTTP"
        def eventDateTime = LocalDateTime.of(2024, 10, 10, 1, 1, 1)

        when: "바로 em.persist 후 id 식별자 값을 조회한다"
        def entityPs = dailyStatRepository.save(DailyStat.createEntity(query, eventDateTime))

        then: "실제 저장이 된다"
        verifyAll(entityPs){
            entityPs.getId() == 1L
            entityPs.getQuery() == query
            entityPs.getEventDateTime() == eventDateTime
        }

    }
}
