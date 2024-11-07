package com.modoojeonse.news.controller;

import com.modoojeonse.common.PageRequestDto;
import com.modoojeonse.news.dto.NewsRequestDto;
import com.modoojeonse.news.dto.NewsResponseDto;
import com.modoojeonse.news.service.NewsSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsSearchController {
    private final NewsSearchService newsSearchService;

    @ResponseBody
    @GetMapping("/news/all")
    private ResponseEntity< List<NewsResponseDto> > searchAll(Pageable pageable) {
        List<NewsResponseDto> news = newsSearchService.searchAll(pageable);

        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/news/search-title")
    private ResponseEntity< List<NewsResponseDto> > searchTitle(@RequestParam(value = "keyword") String keyword, Pageable pageable) {
        List<NewsResponseDto> news = newsSearchService.searchTitle(keyword, pageable);

        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/news/search-body")
    private ResponseEntity< List<NewsResponseDto> > searchBody(@RequestParam(value = "keyword") String keyword, Pageable pageable) {
        List<NewsResponseDto> news = newsSearchService.searchBody(keyword, pageable);

        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/news/search-native")
    private ResponseEntity< List<NewsResponseDto> > searchNative(NewsRequestDto request) {
        List<NewsResponseDto> news = newsSearchService.searchNative(request);

        return new ResponseEntity<>(news, HttpStatus.OK);
    }
}
