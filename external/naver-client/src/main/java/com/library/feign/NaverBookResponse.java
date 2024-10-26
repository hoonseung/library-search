package com.library.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record NaverBookResponse(
    String lastBuildDate,
    int total,
    int start,
    int display,
    List<Item> items
) {


    record Item(
        String title,
        String link,
        String image,
        String author,
        String discount,
        String publisher,
        @JsonProperty("pubdate")
        String pubDate,
        String isbn,
        String description
    ) {

    }

}
