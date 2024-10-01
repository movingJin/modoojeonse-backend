package com.modoojeonse.news.repository;

import com.modoojeonse.news.entity.NewsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.domain.Pageable;

public interface NewsRepository extends ElasticsearchRepository<NewsDocument, String> {
    Page<NewsDocument> findByTitle(String keyword, Pageable pageable);
    Page<NewsDocument> findByBody(String keyword, Pageable pageable);
}
