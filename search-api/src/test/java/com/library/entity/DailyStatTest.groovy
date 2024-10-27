package com.library.entity

import spock.lang.Specification

import java.time.LocalDateTime

class DailyStatTest extends Specification {


    def "create"(){
        given:
        def query = "HTTP"
        def eventDateTime = LocalDateTime.of(2024,10,1,1,1,)

        when:
        def entity = DailyStat.createEntity(query, eventDateTime)

        then:
        verifyAll(entity){
            entity != null
            entity.getQuery() == query
            entity.getEventDateTime() == eventDateTime
        }
    }
}
