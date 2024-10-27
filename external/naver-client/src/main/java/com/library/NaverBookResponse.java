package com.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record NaverBookResponse(
    String lastBuildDate,
    int total,
    int start,
    int display,
    List<Item> items
) {


    public record Item(
        String title,
        String author,
        String publisher,
        @JsonProperty("pubdate")
        String pubDate,
        String isbn
    ) {

    }

}
