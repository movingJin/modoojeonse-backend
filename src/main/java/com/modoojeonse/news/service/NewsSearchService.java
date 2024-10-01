package com.modoojeonse.news.service;

import com.modoojeonse.news.dto.NewsRequestDto;
import com.modoojeonse.news.dto.NewsResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsSearchService {
    public List<NewsResponseDto> searchTitle(String keyword, Pageable pageable);
    public List<NewsResponseDto> searchBody(String keyword, Pageable pageable);
    public List<NewsResponseDto> searchNative(NewsRequestDto newsRequestDto);
}
