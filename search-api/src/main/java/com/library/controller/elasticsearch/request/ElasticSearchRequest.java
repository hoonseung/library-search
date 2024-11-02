package com.library.controller.elasticsearch.request;
/**
 * elastic search 검색 api 요청 쿼리
 *
 * @param query : 검색 쿼리
 */

import com.fasterxml.jackson.annotation.JsonProperty;

public record ElasticSearchRequest(
    @JsonProperty("query")
    SearchQuery searchQuery,
    @JsonProperty("from")
    int page,
    @JsonProperty("size")
    int size
) {


    public static ElasticSearchRequest of(String title, int page, int size) {
        return new ElasticSearchRequest(
            new SearchQuery(
                new QueryMatch(title)
            ),
            page,
            size
        );
    }


    record SearchQuery(
        @JsonProperty("match")
        QueryMatch queryMatch
    ) {

    }


    record QueryMatch(
        String title
    ) {

    }


}

