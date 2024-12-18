package com.modoojeonse.reviews.controller;

import com.modoojeonse.member.security.JwtProvider;
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
    private final JwtProvider jwtProvider;
    private final ReviewService reviewService;

    @ResponseBody
    @GetMapping("/review/all")
    private ResponseEntity< List<ReviewResponseDto> > searchAll(Pageable pageable) {
        List<ReviewResponseDto> reviews = reviewService.searchAll(pageable);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/review/search-id")
    private ResponseEntity< ReviewResponseDto > searchId(@RequestParam(value = "id") String id) {
        ReviewResponseDto reviews = reviewService.searchId(id);

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
    private ResponseEntity< List<ReviewResponseDto> > searchNative(@ModelAttribute ReviewRequestDto request) {
        List<ReviewResponseDto> reviews = reviewService.searchNative(request);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/review/save")
    public ResponseEntity<Boolean> save(@RequestBody ReviewRequestDto request) throws Exception {
        return new ResponseEntity<>(reviewService.saveReview(request), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/review/edit")
    public ResponseEntity<Boolean> edit(@RequestBody ReviewRequestDto request, @RequestHeader (name="Authorization") String token) throws Exception {
        String email = jwtProvider.getAccount(token);
        if(email.equals(request.getAuthor())){
            return new ResponseEntity<>(reviewService.editReview(request), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @PostMapping(value = "/review/delete")
    public ResponseEntity<Boolean> delete(@RequestBody ReviewRequestDto request, @RequestHeader (name="Authorization") String token) throws Exception {
        String email = jwtProvider.getAccount(token);
        if(email.equals(request.getAuthor())){
            return new ResponseEntity<>(reviewService.deleteReview(request), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }
}
