package com.library.repository.elasticsearch;

import com.library.controller.response.PageResultV2;
import com.library.controller.response.SearchResponse;

public interface ElasticBookRepository {

    PageResultV2<SearchResponse> search(String title, int page, int size);

    void save(SearchResponse searchResponse);
}
