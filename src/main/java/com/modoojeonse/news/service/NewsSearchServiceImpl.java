package com.modoojeonse.news.service;

import com.modoojeonse.news.dto.NewsRequestDto;
import com.modoojeonse.news.dto.NewsResponseDto;
import com.modoojeonse.news.entity.NewsDocument;
import com.modoojeonse.news.repository.NewsNativeQueryRepository;
import com.modoojeonse.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsSearchServiceImpl implements NewsSearchService {
    private final NewsRepository newsRepository;
    private final NewsNativeQueryRepository newsNativeQueryRepository;

    @Override
    public List<NewsResponseDto> searchAll(Pageable pageable) {
        Page<NewsDocument> searchHits = newsRepository.findAll(pageable);
        return searchHits.stream()
                .map(NewsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsResponseDto> searchTitle(String keyword, Pageable pageable) {
        Page<NewsDocument> searchHits = newsRepository.findByTitle(keyword, pageable);
        return searchHits.stream()
                .map(NewsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsResponseDto> searchBody(String keyword, Pageable pageable) {
        Page<NewsDocument> searchHits = newsRepository.findByBody(keyword, pageable);
        return searchHits.stream().map(NewsResponseDto::new).toList();
    }

    @Override
    public List<NewsResponseDto> searchNative(NewsRequestDto newsRequestDto) {
        SearchHits<NewsDocument> searchHits = newsNativeQueryRepository.findByNativeCondition(newsRequestDto);
        return searchHits.stream().map(hit -> new NewsResponseDto(hit.getContent())).toList();
    }
}
