package com.library.service


import com.library.repository.BookRepository
import spock.lang.Specification

class BookQueryServiceTest extends Specification {

    BookRepository bookRepository = Mock(BookRepository)

    BookQueryService bookQueryService

    void setup() {
        bookQueryService = new BookQueryService(bookRepository)
    }

    def "search 호출 시 별도 가공 없이 repository로 인자가 넘어간다."() {
        given:
        def givenQuery = "HTTP"
        def givenPage = 1
        def givenSize = 10

        when:
        def result = bookQueryService.search(givenQuery, givenPage, givenSize)

        then:
        1 * bookRepository.search(*_) >> {
            String query, int page, int size ->
                assert query == givenQuery
                assert page == givenPage
                assert size == givenSize
        }
    }
}
