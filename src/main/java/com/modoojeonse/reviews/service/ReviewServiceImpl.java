package com.modoojeonse.reviews.service;

import com.modoojeonse.common.BusinessLogicException;
import com.modoojeonse.common.ExceptionCode;
import com.modoojeonse.geo.entity.GeoDocument;
import com.modoojeonse.reviews.dto.ReviewRequestDto;
import com.modoojeonse.reviews.dto.ReviewResponseDto;
import com.modoojeonse.reviews.entity.ReviewDocument;
import com.modoojeonse.reviews.repository.ReviewNativeQueryRepository;
import com.modoojeonse.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewNativeQueryRepository reviewNativeQueryRepository;

    @Override
    public List<ReviewResponseDto> searchAll(Pageable pageable) {
        Page<ReviewDocument> searchHits = reviewRepository.findAll(pageable);
        return searchHits.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDto searchId(String id) {
        Optional<ReviewDocument> searchHits = reviewRepository.findById(id);
        return searchHits
                .map(ReviewResponseDto::new) // Transform ReviewDocument to ReviewResponseDto
                .orElseThrow(() -> new RuntimeException("Review not found for id: " + id)); // Handle empty case
    }

    @Override
    public List<ReviewResponseDto> searchTitle(String keyword, Pageable pageable) {
        Page<ReviewDocument> searchHits = reviewRepository.findByTitle(keyword, pageable);
        return searchHits.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDto> searchBody(String keyword, Pageable pageable) {
        Page<ReviewDocument> searchHits = reviewRepository.findByBody(keyword, pageable);
        return searchHits.stream().map(ReviewResponseDto::new).toList();
    }

    @Override
    public List<ReviewResponseDto> searchNative(ReviewRequestDto reviewRequestDto) {
        SearchHits<ReviewDocument> searchHits = reviewNativeQueryRepository.findByNativeCondition(reviewRequestDto);
        List<ReviewResponseDto> searchResults = new ArrayList<>();
        for(var searchHit: searchHits){
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(searchHit.getContent());
            reviewResponseDto.setSort((Long) searchHit.getSortValues().get(0));
            searchResults.add(reviewResponseDto);
        }
        return searchResults;
    }

    @Override
    public boolean saveReview(ReviewRequestDto reviewRequestDto) throws Exception {
        checkDuplicatedReview(reviewRequestDto);
        reviewRepository.save(new ReviewDocument(reviewRequestDto));
        return true;
    }

    @Override
    public boolean editReview(ReviewRequestDto reviewRequestDto) throws Exception{
        if(!reviewRequestDto.getId().isEmpty()){
            reviewRepository.save(new ReviewDocument(reviewRequestDto));
        }else{
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND);
        }
        return true;
    }

    @Override
    public boolean deleteReview(ReviewRequestDto reviewRequestDto) throws Exception {
        if(!reviewRequestDto.getId().isEmpty()){
            reviewRepository.deleteById(reviewRequestDto.getId());
        }else{
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND);
        }
        return true;
    }

    private void checkDuplicatedReview(ReviewRequestDto reviewRequestDto) {
        SearchHits<ReviewDocument> searchHits = reviewNativeQueryRepository.findByAddressDetailKeyword(
                reviewRequestDto.getAuthor(),
                reviewRequestDto.getAddress(),
                reviewRequestDto.getAddressDetail().strip());

        if (!searchHits.isEmpty()) {
            log.debug("ReviewSearchServiceImpl.checkDuplicatedGeo exception occur address: {}", reviewRequestDto.getAddressDetail());
            throw new BusinessLogicException(ExceptionCode.REVIEW_EXISTS);
        }
    }
}
