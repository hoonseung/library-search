package com.library.repository

import com.library.NaverBookResponse
import com.library.feign.NaverClient
import spock.lang.Specification

import java.time.LocalDate

import static com.library.NaverBookResponse.Item

class NaverBookRepositoryTest extends Specification {

    NaverClient naverClient = Mock(NaverClient)

    BookRepository bookRepository

    void setup() {
        bookRepository = new NaverBookRepository(naverClient)
    }


    def "search 호출 시 적잘한 데이터 형식으로 반환한다."() {
        given:
        def items = [
                new Item(
                        "제목1",
                        "데이빗 고울리1",
                        "인사이트",
                        "20141215",
                        "9788966261208"
                ),
                new Item(
                        "제목2",
                        "데이빗 고울리2",
                        "인사이트",
                        "20141215",
                        "9788966261208"
                )
        ]

        def response = new NaverBookResponse(
                "Sat, 26 Oct 2024 22:08:13 +0900",
                2,
                1,
                2,
                items
        )

        and:
        1 * naverClient.search("HTTP", 1, 2) >> response

        when:
        def result = bookRepository.search("HTTP", 1, 2)

        then:
        verifyAll(result) {
            result.size() == 2
            result.page() == 1
            result.totalElements() == 2
            result.contents().size() == 2
            result.contents().get(0).pubDate() == LocalDate.of(2014, 12, 15)
        }
    }
}
