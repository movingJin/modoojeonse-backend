package com.modoojeonse.reviews.service;

import com.modoojeonse.reviews.dto.ReviewRequestDto;
import com.modoojeonse.reviews.dto.ReviewResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    public List<ReviewResponseDto> searchAll(Pageable pageable);
    public List<ReviewResponseDto> searchTitle(String keyword, Pageable pageable);
    public List<ReviewResponseDto> searchBody(String keyword, Pageable pageable);
    public List<ReviewResponseDto> searchNative(ReviewRequestDto reviewRequestDto);
    public boolean saveReview(ReviewRequestDto reviewRequestDto) throws Exception;
    public boolean editReview(ReviewRequestDto reviewRequestDto) throws Exception;
    public boolean deleteReview(ReviewRequestDto reviewRequestDto) throws Exception;
}
