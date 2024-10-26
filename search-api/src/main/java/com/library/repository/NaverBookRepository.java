package com.library.repository;

import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.feign.NaverBookResponse;
import com.library.feign.NaverClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NaverBookRepository implements BookRepository {

    private final NaverClient naverClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        NaverBookResponse response = naverClient.search(query, page, size);
        return new PageResult<>(
            response.start(),
            response.display(),
            response.total(),
            response.items()
                .stream()
                .map(SearchResponse::create)
                .toList()
        );
    }
}
