package com.library.feign;

import com.library.NaverBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverClient", url = "${external.naver.url}", configuration = NaverClientConfiguration.class)
public interface NaverClient {

    @GetMapping("/v1/search/book.json")
    NaverBookResponse search(@RequestParam(name = "query") String query,
        @RequestParam(name = "start") int start,
        @RequestParam(name = "display") int display);
}
