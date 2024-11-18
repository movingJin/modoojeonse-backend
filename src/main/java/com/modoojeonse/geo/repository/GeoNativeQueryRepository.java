package com.modoojeonse.geo.repository;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.geo.entity.GeoDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GeoNativeQueryRepository {
    private final ElasticsearchOperations operations;

    public SearchHits<GeoDocument> findByDistance(GeoRequestDto geoRequestDto) {
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

        NativeQuery query = nativeQueryBuilder.build();
        return operations.search(query, GeoDocument.class, IndexCoordinates.of("modoojeonse-geo"));
    }

    public SearchHits<GeoDocument> findByAddressKeyword(String address){
        NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
        List<Query> filterList = new ArrayList<>();

        Query filterQuery = QueryBuilders.match()
                .query(address)
                .field("address.keyword")
                .build()._toQuery();
        filterList.add(filterQuery);

        Query boolQuery = QueryBuilders.bool()
                .filter(filterList)
                .build()._toQuery();

        nativeQueryBuilder
                .withReactiveBatchSize(10)
                .withQuery(boolQuery);

        NativeQuery query = nativeQueryBuilder.build();
        return operations.search(query, GeoDocument.class, IndexCoordinates.of("modoojeonse-geo"));
    }
}
