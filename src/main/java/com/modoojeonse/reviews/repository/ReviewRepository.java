package com.modoojeonse.reviews.repository;

import com.modoojeonse.geo.entity.GeoDocument;
import com.modoojeonse.news.entity.NewsDocument;
import com.modoojeonse.reviews.entity.ReviewDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReviewRepository extends ElasticsearchRepository<ReviewDocument, String> {
    Page<ReviewDocument> findAll(Pageable pageable);
    Page<ReviewDocument> findByTitle(String keyword, Pageable pageable);
    Page<ReviewDocument> findByBody(String keyword, Pageable pageable);
}
