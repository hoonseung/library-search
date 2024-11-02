package com.library.controller.response;

import com.library.KakaoBookResponse.Document;
import com.library.NaverBookResponse.Item;
import com.library.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Schema(description = "도서검색결과 응답 모델")
@Builder
public record SearchResponse(
    @Schema(description = "도서 제목", example = "HTTP 완벽 가이드")
    String title,
    @Schema(description = "도서 저자", example = "데이빗 고올리")
    String author,
    @Schema(description = "도서 출판사", example = "인사이트")
    String publisher,
    @Schema(description = "도서 출판일", example = "2015-01-01")
    LocalDate pubDate,
    @Schema(description = "도서 ISBN", example = "9788966261208")
    String isbn
) {

    public static SearchResponse create(Item item) {
        return SearchResponse.builder()
            .title(item.title())
            .author(item.author())
            .publisher(item.publisher())
            .pubDate(DateUtils.parseYYYYMMDD(item.pubDate()))
            .isbn(item.isbn())
            .build();
    }

    public static SearchResponse create(Document document) {
        return SearchResponse.builder()
            .title(document.title())
            .author(document.authors().isEmpty() ? "저자 없음" : document.authors().getFirst())
            .publisher(document.publisher())
            .pubDate(document.dateTime().toLocalDate())
            .isbn(document.isbn())
            .build();
    }

    public static SearchResponse create(String title, String author, String publisher, String pubDate, String isbn) {
        return SearchResponse.builder()
            .title(title)
            .author(author.isEmpty() ? "저자 없음" : author)
            .publisher(publisher)
            .pubDate(DateUtils.parseYYYYdashMMdashDD(pubDate))
            .isbn(isbn)
            .build();
    }

}
