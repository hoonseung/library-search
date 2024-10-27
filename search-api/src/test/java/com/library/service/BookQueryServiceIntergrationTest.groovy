package com.library.service

import com.library.controller.response.PageResult
import com.library.repository.BookRepository
import com.library.repository.KakaoBookRepository
import com.library.repository.NaverBookRepository
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

// 각 테스트 메서드가 실행된 후에 Spring 애플리케이션 컨텍스트를 다시 로드하도록 지시합니다.
// 이를 통해 테스트 메서드 간의 상태가 격리되어, 한 테스트 메서드의 변경 사항이 다른 테스트 메서드에 영향을 미치지 않도록 합니다.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class BookQueryServiceIntergrationTest extends Specification {

    @SpringBean
    NaverBookRepository naverBookRepository = Mock()
    @SpringBean
    KakaoBookRepository kakaoBookRepository = Mock()

    @Autowired
    BookQueryService bookQueryService;
    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry

    def "정상상황에서는 CircuitBreaker 상태는 닫혀있어야하고 네이버쪽으로 호출되어야한다."(){
        given:
        def query = "HTTP"
        def page = 1
        def size = 10

        when:
        bookQueryService.search(query, page, size)

        then:
        1 * naverBookRepository.search(query, page, size) >> new PageResult(1, 10, 0, [])

        and:
        def circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers().stream().findFirst().get()
        circuitBreaker.state == CircuitBreaker.State.CLOSED

        and:
        0 * kakaoBookRepository.search(*_)
    }

    def "CircuitBreaker 의상태가 개방되었을 경우 카카오쪽으로 호출되어야한다."(){
        given:
        def query = "HTTP"
        def page = 1
        def size = 10
        def kakaoResponse = new PageResult(1, 10, 0, [])

        def config = CircuitBreakerConfig.custom()
                .slidingWindowSize(1)
                .minimumNumberOfCalls(1)
                .failureRateThreshold(50)
                .build()
        circuitBreakerRegistry.circuitBreaker("naverSearch", config)

        and: "네이버쪽은 1번 호출된다."
        1 * naverBookRepository.search(*_) >> {throw new RuntimeException()}

        when:
        def result = bookQueryService.search(query, page, size)

        then: "카카오쪽으로 호출되어야함"
        1 * kakaoBookRepository.search(query, page, size) >> kakaoResponse

        and: "네이버쪽 호출이 실패하여 CircuitBreaker 가 개방되었음"
        def circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers().stream().findFirst().get()
        circuitBreaker.state == CircuitBreaker.State.OPEN

        result == kakaoResponse
    }





}
