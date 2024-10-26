package com.library.controller.response

import spock.lang.Specification

import java.time.LocalDate

class SearchResponseTest extends Specification {


    def "SearchResponse 객체 생성된다."() {
        given:
        def givenTitle = "HTTP 완벽 가이드"
        def givenAuthor = "데이빗 고울리"
        def givenPublisher = "인사이트"
        def givenPubDate = LocalDate.of(2014, 12, 15)
        def givenIsbn = "978896621208"

        when:
        def response = SearchResponse.builder()
                .title(givenTitle)
                .author(givenAuthor)
                .publisher(givenPublisher)
                .pubDate(givenPubDate)
                .isbn(givenIsbn)
                .build()

        then:
        verifyAll(response) {
            response.title() == givenTitle
            response.author() == givenAuthor
            response.publisher() == givenPublisher
            response.pubDate() == givenPubDate
            response.isbn() == givenIsbn
        }
    }
}
