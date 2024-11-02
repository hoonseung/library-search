package com.library.repository.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.controller.elasticsearch.request.ElasticSearchRequest;
import com.library.controller.response.PageResultV2;
import com.library.controller.response.SearchResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookElasticRepository implements ElasticBookRepository {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void save(SearchResponse searchResponse) {
        webClient.post()
            .uri("/books/_doc")
            .bodyValue(searchResponse)
            .retrieve()
            .bodyToMono(SearchResponse.class)
            .doOnError(throwable -> log.error("book save error: {}", throwable.getMessage()))
            .subscribe(bookSaveResponse -> log.info("book save response: {}", bookSaveResponse));
    }

    @Override
    public PageResultV2<SearchResponse> search(String query, int page, int size) {
        ElasticSearchRequest elasticSearchRequest = ElasticSearchRequest.of(query, page, size);
        try {
            String bodyValue = objectMapper.writeValueAsString(elasticSearchRequest);

            List<SearchResponse> searchResponses = webClient.post()
                .uri("/books/_search")
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(throwable -> log.error("book search error: {}", throwable.getMessage()))
                .flatMap(this::extractBody)
                .block();

            return new PageResultV2<>(page, size, searchResponses);
        } catch (JsonProcessingException e) {
            log.error("extractBody error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private Mono<List<SearchResponse>> extractBody(String body) {
        return Mono.fromCallable(() -> {
            List<SearchResponse> searchResponses = new ArrayList<>();
            JsonNode rootNode = objectMapper.readTree(body);
            JsonNode histsNode = rootNode.path("hits").path("hits");

            if (histsNode.isArray()) {
                histsNode.forEach(hit -> {
                    JsonNode sourceNode = hit.path("_source");

                    SearchResponse bookSearchResponse = SearchResponse.create(
                        sourceNode.path("title").asText(),
                        sourceNode.path("author").asText(),
                        sourceNode.path("publisher").asText(),
                        sourceNode.path("pubDate").asText(),
                        sourceNode.path("isbn").asText()
                    );

                    searchResponses.add(bookSearchResponse);
                });
            }
            return searchResponses;
        });
    }
}
