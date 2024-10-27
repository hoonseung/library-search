package com.library.repository

import com.library.KakaoBookResponse
import com.library.NaverBookResponse
import com.library.ferign.KakaoClient
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class KakaoBookRepositoryTest extends Specification {


    KakaoClient kakaoClient = Mock()
    KakaoBookRepository kakaoBookRepository

    void setup(){
        kakaoBookRepository = new KakaoBookRepository(kakaoClient)
    }

    def "search 호출 시 적잘한 데이터 형식으로 반환한다."() {
        given:
        def documents = [
                new KakaoBookResponse.Document(
                        "제목1",
                        ["저자1"],
                        "isbn1",
                        "출판사",
                        ZonedDateTime.of(2014, 12, 15, 0 ,0 ,0, 0, ZoneId.systemDefault())
                ),
                new KakaoBookResponse.Document(
                        "제목2",
                        ["저자2"],
                        "isbn2",
                        "출판사",
                        ZonedDateTime.of(2014, 12, 15, 0 ,0 ,0, 0, ZoneId.systemDefault())
                )
        ]

        def meta = new KakaoBookResponse.Meta(
                true,
                1,
                10
        )
        def response = new KakaoBookResponse(documents, meta)


        and:
        1 * kakaoClient.search("HTTP", 1, 2) >> response

        when:
        def result = kakaoBookRepository.search("HTTP", 1, 2)

        then:
        verifyAll(result) {
            result.size() == 2
            result.page() == 1
            result.totalElements() == 10
            result.contents().size() == 2
            result.contents().get(0).pubDate() == LocalDate.of(2014, 12, 15)
        }
    }
}
