package com.library.controller.elasticsearch.response;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * elastic search 저장 api 응답
 * @param index : 인덱스
 * @param id : id
 * @param result : 결과
 * @param seqNo : 시퀀스 번호
 */
public record BookSaveResponse(

    @JsonProperty("_index")
    String index,
    @JsonProperty("_id")
    String id,
    @JsonProperty("_result")
    String result,
    @JsonProperty("_seq_no")
    int seqNo
) {







}
