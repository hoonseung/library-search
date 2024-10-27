package com.library;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.List;

public record KakaoBookResponse(
    List<Document> documents,
    Meta meta
) {


    public record Document(
        String title,
        List<String> authors,
        String isbn,
        String publisher,
        @JsonProperty("datetime")
        ZonedDateTime dateTime

    ) {

    }

    @JsonIncludeProperties({"is_end", "pageable_count", "total_count"})
    public record Meta(
        boolean isEnd,
        int pageableCount,
        int totalCount
    ) {

    }
}
