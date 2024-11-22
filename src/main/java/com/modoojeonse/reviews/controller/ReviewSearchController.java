package com.modoojeonse.reviews.controller;

import com.modoojeonse.reviews.dto.ReviewRequestDto;
import com.modoojeonse.reviews.dto.ReviewResponseDto;
import com.modoojeonse.reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewSearchController {
    private final ReviewService reviewService;

    @ResponseBody
    @GetMapping("/review/all")
    private ResponseEntity< List<ReviewResponseDto> > searchAll(Pageable pageable) {
        List<ReviewResponseDto> reviews = reviewService.searchAll(pageable);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/review/search-title")
    private ResponseEntity< List<ReviewResponseDto> > searchTitle(@RequestParam(value = "keyword") String keyword, Pageable pageable) {
        List<ReviewResponseDto> reviews = reviewService.searchTitle(keyword, pageable);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/review/search-body")
    private ResponseEntity< List<ReviewResponseDto> > searchBody(@RequestParam(value = "keyword") String keyword, Pageable pageable) {
        List<ReviewResponseDto> reviews = reviewService.searchBody(keyword, pageable);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/review/search-native")
    private ResponseEntity< List<ReviewResponseDto> > searchNative(ReviewRequestDto request) {
        List<ReviewResponseDto> reviews = reviewService.searchNative(request);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/review/save")
    public ResponseEntity<Boolean> save(@RequestBody ReviewRequestDto request) throws Exception {
        return new ResponseEntity<>(reviewService.saveReview(request), HttpStatus.OK);
    }
}
