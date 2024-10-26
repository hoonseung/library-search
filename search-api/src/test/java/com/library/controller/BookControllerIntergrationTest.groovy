package com.library.controller

import com.library.controller.request.SearchRequest
import com.library.controller.response.PageResult
import com.library.controller.response.SearchResponse
import com.library.service.BookQueryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntergrationTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookQueryService bookQueryService = Mock()

    def "name"() {
        given:
        def request = new SearchRequest(query: "HTTP", page: 1, size: 10)


        and:
        1 * bookQueryService.search(*_) >> new PageResult<>(1, 10, 10, [Mock(SearchResponse)])


        when:
        def result = mockMvc.perform(get("/v1/books")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage().toString())
                .queryParam("size", request.getSize().toString())
        )


        then:
        result.andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.page').value(1))
                .andExpect(jsonPath('$.size').value(10))
                .andExpect(jsonPath('$.totalElements').value(10))
                .andExpect(jsonPath('$.contents').isArray())
    }

    def "query가 비어있을 때 BadRequest 응답된다"() {
        given:
        def request = new SearchRequest(query: "", page: 1, size: 10)

        when:
        def result = mockMvc.perform(get("/v1/books")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage().toString())
                .queryParam("size", request.getSize().toString())
        )


        then:
        result.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.errorMessage').value("입력은 필수 입니다."))
    }

    def "page가 음수일 경우 BadRequest 응답된다"() {
        given:
        def request = new SearchRequest(query: "title", page: -1, size: 10)

        when:
        def result = mockMvc.perform(get("/v1/books")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage().toString())
                .queryParam("size", request.getSize().toString())
        )


        then:
        result.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.errorMessage').value("페이지번호는 1이상이어야 합니다."))
    }

    def "size가 초과일 경우 BadRequest 응답된다"() {
        given:
        def request = new SearchRequest(query: "title", page: 1, size: 51)

        when:
        def result = mockMvc.perform(get("/v1/books")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage().toString())
                .queryParam("size", request.getSize().toString())
        )


        then:
        result.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath('$.errorMessage').value("페이지번호는 50이하이어야 합니다."))
    }


}
