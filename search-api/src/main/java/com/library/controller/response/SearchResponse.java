package com.library.controller.response;

import com.library.feign.NaverBookResponse.Item;
import com.library.util.DateUtils;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SearchResponse(
    String title,
    String author,
    String publisher,
    LocalDate pubDate,
    String isbn
) {

    public static SearchResponse create(Item item) {
        return SearchResponse.builder()
            .title(item.title())
            .author(item.author())
            .publisher(item.publisher())
            .pubDate(DateUtils.parseYYYMMDD(item.pubDate()))
            .isbn(item.isbn())
            .build();
    }

}
