package com.modoojeonse.geo.repository;

import com.modoojeonse.geo.entity.GeoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GeoRepository extends ElasticsearchRepository<GeoDocument, String> {

}
