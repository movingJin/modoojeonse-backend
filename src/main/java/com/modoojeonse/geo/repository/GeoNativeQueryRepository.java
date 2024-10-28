package com.modoojeonse.geo.repository;


import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.geo.entity.GeoDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GeoNativeQueryRepository {
    private final ElasticsearchOperations operations;

    public SearchHits<GeoDocument> findByNativeCondition(GeoRequestDto geoRequestDto) {
        NativeQuery query = createConditionNativeQuery(geoRequestDto);
        return operations.search(query, GeoDocument.class);
    }

    private NativeQuery createConditionNativeQuery(GeoRequestDto geoRequestDto) {
        NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
        List<Query> mustList = new ArrayList<>();
        List<Query> filterList = new ArrayList<>();

        Query geoDistanceQuery = QueryBuilders.geoDistance()
                .field("location")
                .distance("10km")
                .location(geoLocation ->
                        geoLocation.latlon(latLonGeoLocation ->
                                latLonGeoLocation
                                        .lon(geoRequestDto.getLocation().getLon())
                                        .lat(geoRequestDto.getLocation().getLat())
                        )
                )
                .build()._toQuery();
        filterList.add(geoDistanceQuery);

        Query boolQuery = QueryBuilders.bool()
                .filter(filterList)
                .must(mustList)
                .build()._toQuery();

        nativeQueryBuilder
                .withReactiveBatchSize(10)
                .withQuery(boolQuery);

        return nativeQueryBuilder.build();
    }
}
