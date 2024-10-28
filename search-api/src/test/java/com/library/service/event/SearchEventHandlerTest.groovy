package com.library.service.event

import com.library.entity.DailyStat
import com.library.service.DailyStatCommandService
import spock.lang.Specification

import java.time.LocalDateTime

class SearchEventHandlerTest extends Specification {



    def "handleEvent"(){
        given:
        def dailyStatCommandService = Mock(DailyStatCommandService)
        def searchEventHandler = new SearchEventHandler(dailyStatCommandService)
        def searchEvent = new SearchEvent("HTTP", LocalDateTime.now())

        when:
        searchEventHandler.eventHandle(searchEvent)

        then:
        1 * dailyStatCommandService.save(_ as DailyStat)
    }
}
