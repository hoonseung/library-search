package com.library.service;

import com.library.controller.response.PageResultV2;
import com.library.controller.response.SearchResponse;
import com.library.repository.elasticsearch.ElasticBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BookElasticSearchService {


    private final ElasticBookRepository elasticBookRepository;


    public void save(SearchResponse SearchResponse) {
        elasticBookRepository.save(SearchResponse);
    }

    public PageResultV2<SearchResponse> search(String title, int page, int size) {
        return elasticBookRepository.search(title, page, size);
    }
}
