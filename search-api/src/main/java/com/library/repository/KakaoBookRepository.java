package com.library.repository;

import com.library.KakaoBookResponse;
import com.library.controller.response.PageResult;
import com.library.controller.response.SearchResponse;
import com.library.ferign.KakaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class KakaoBookRepository implements BookRepository {

    private final KakaoClient kakaoClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        KakaoBookResponse response = kakaoClient.search(query, page, size);
        return new PageResult<>(
            page,
            size,
            response.meta().totalCount(),
            response.documents().stream()
                .map(SearchResponse::create)
                .toList()
        );
    }
}
