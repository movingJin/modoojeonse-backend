package com.modoojeonse.reviews.repository;


import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.modoojeonse.reviews.dto.ReviewRequestDto;
import com.modoojeonse.reviews.entity.ReviewDocument;
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
public class ReviewNativeQueryRepository {
    private final ElasticsearchOperations operations;

    public SearchHits<ReviewDocument> findByNativeCondition(ReviewRequestDto reviewRequestDto) {
        NativeQuery query = createConditionNativeQuery(reviewRequestDto);
        return operations.search(query, ReviewDocument.class);
    }

    private NativeQuery createConditionNativeQuery(ReviewRequestDto reviewRequestDto) {
        NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
        List<Query> mustList = new ArrayList<>();
        List<Query> filterList = new ArrayList<>();

        if(reviewRequestDto.getId() != null){
            Query query = QueryBuilders.match()
                    .query(reviewRequestDto.getId())
                    .field("id")
                    .build()._toQuery();
            mustList.add(query);
        }
        if(reviewRequestDto.getTitle() != null){
            Query query = QueryBuilders.match()
                    .query(reviewRequestDto.getTitle())
                    .field("title")
                    .build()._toQuery();
            mustList.add(query);
        }
        if(reviewRequestDto.getBody() != null) {
            Query query = QueryBuilders.match()
                    .query(reviewRequestDto.getBody())
                    .field("body_text")
                    .build()._toQuery();
            mustList.add(query);
        }
        if(reviewRequestDto.getAddress() != null) {
            Query query = QueryBuilders.term()
                    .field("address")
                    .value(reviewRequestDto.getAddress())
                    .build()._toQuery();
            filterList.add(query);
        }
/*
        if (reviewRequestDto.getFromTime() != null) {
            Query startedAtFilterQuery = QueryBuilders
                    .range()
                    .field("@timestamp")
                    .gte(JsonData.of(reviewRequestDto.getFromTime()))
                    .build()._toQuery();
            filterList.add(startedAtFilterQuery);
        }
        if (reviewRequestDto.getToTime() != null) {
            Query endedAtFilterQuery = QueryBuilders
                    .range()
                    .field("@timestamp")
                    .lt(JsonData.of(reviewRequestDto.getToTime()))
                    .build()._toQuery();
            filterList.add(endedAtFilterQuery);
        }
*/
        Query boolQuery = QueryBuilders.bool()
                .filter(filterList)
                .must(mustList)
                .build()._toQuery();

        SortOptions sortOptions = new SortOptions.Builder()
                .field(SortOptionsBuilders.field()
                        .field("@timestamp")
                        .order(SortOrder.Desc)
                        .build()
                ).build();

        nativeQueryBuilder
                .withReactiveBatchSize(10)
                .withQuery(boolQuery)
                .withSort(sortOptions);
        if (reviewRequestDto.getSearchAfter() != null) {
            nativeQueryBuilder.withSearchAfter(List.of(reviewRequestDto.getSearchAfter()));
        }

        return nativeQueryBuilder.build();
    }

    public SearchHits<ReviewDocument> findByAddressDetailKeyword(String author, String address, String addressDetail){
        NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
        List<Query> filterList = new ArrayList<>();

        Query addressFilter = QueryBuilders.match()
                .query(address)
                .field("address")
                .build()._toQuery();
        filterList.add(addressFilter);

        Query addressDetailFilter = QueryBuilders.match()
                .query(addressDetail)
                .field("address_detail.keyword")
                .build()._toQuery();
        filterList.add(addressDetailFilter);

        Query authorFilter = QueryBuilders.match()
                .query(author)
                .field("author.keyword")
                .build()._toQuery();
        filterList.add(authorFilter);

        Query boolQuery = QueryBuilders.bool()
                .filter(filterList)
                .build()._toQuery();

        nativeQueryBuilder
                .withReactiveBatchSize(10)
                .withQuery(boolQuery);

        NativeQuery query = nativeQueryBuilder.build();
        return operations.search(query, ReviewDocument.class);
    }
}
