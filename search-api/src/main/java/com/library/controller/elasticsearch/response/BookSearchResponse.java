package com.library.controller.elasticsearch.response;

import com.library.util.DateUtils;
import java.time.LocalDate;

public record BookSearchResponse(
    String title,
    String author,
    String publisher,
    LocalDate pubDate,
    String isbn
) {

    public static BookSearchResponse of(String title, String author, String publisher, String pubDate, String isbn) {
        return new BookSearchResponse(
            title,
            author,
            publisher,
            DateUtils.parseYYYYdashMMdashDD(pubDate),
            isbn);
    }
}
