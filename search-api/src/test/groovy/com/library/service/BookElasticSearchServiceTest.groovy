package com.library.service


import com.library.controller.response.PageResultV2
import com.library.controller.response.SearchResponse
import com.library.repository.elasticsearch.ElasticBookRepository
import spock.lang.Specification

import java.time.LocalDate

class BookElasticSearchServiceTest extends Specification {


    ElasticBookRepository elasticBookRepository = Mock()
    BookElasticSearchService bookElasticSearchService;

    void setup() {
        bookElasticSearchService = new BookElasticSearchService(elasticBookRepository)
    }


    def "엘라스틱 서치를 통해 조회하는 서비스에서 search 호출 시 PageResultV2를 반환한다."() {
        given:
        def givenTitle = "HTTP"
        def givenPage = 0
        def givenSize = 4
        def pageResult = new PageResultV2<>(givenPage, givenSize, [Mock(SearchResponse.class)])

        and:
        1 * elasticBookRepository.search(givenTitle, givenPage, givenSize) >> pageResult

        when:
        def response = bookElasticSearchService.search(givenTitle, givenPage, givenSize)

        then:
        verifyAll(response) {
            assert response != null
            assert response.page() == givenPage
            assert response.size() == givenSize
        }
    }

    def "엘라스틱 서치를 통해 조회하는 서비스에서 save 호출 시 파라미터가 잘넘어오는지"() {
        given:
        def searchResponse = new SearchResponse(
                "HTTP", "저자", "존", LocalDate.now(), "123")

        when:
        bookElasticSearchService.save(searchResponse)

        then: "searchResponse가 잘넘어왔는지 확인"
        1 * elasticBookRepository.save(searchResponse)
        verifyAll(searchResponse) {
            SearchResponse expect ->
                {
                    assert expect.title() == "HTTP"
                    assert expect.publisher() == "존"
                }
        }
    }

}
