package com.modoojeonse.news.repository;


import java.util.ArrayList;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.modoojeonse.news.dto.NewsRequestDto;
import com.modoojeonse.news.entity.NewsDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Repository;
//import org.elasticsearch.index.query.MatchQueryBuilder;

import java.util.List;

import static org.springframework.data.elasticsearch.client.elc.Queries.matchQuery;

@Repository
@RequiredArgsConstructor
public class NewsNativeQueryRepository {
    private final ElasticsearchOperations operations;

    public SearchHits<NewsDocument> findByNativeCondition(NewsRequestDto newsRequestDto) {
        NativeQuery query = createConditionNativeQuery(newsRequestDto);
        //System.out.println("testbug: " + query.getHighlightQuery());
        return operations.search(query, NewsDocument.class);
    }

    private NativeQuery createConditionNativeQuery(NewsRequestDto newsRequestDto) {
        NativeQueryBuilder queryBuilder = new NativeQueryBuilder();
        List<Query> mustList = new ArrayList<>();
        List<Query> filterList = new ArrayList<>();

        if(newsRequestDto.getId() != null){
            Query query = QueryBuilders.match()
                    .query(newsRequestDto.getId())
                    .field("id")
                    .build()._toQuery();
            mustList.add(query);
        }
        if(newsRequestDto.getTitle() != null){
            Query query = QueryBuilders.match()
                    .query(newsRequestDto.getTitle())
                    .field("title")
                    .build()._toQuery();
            mustList.add(query);
        }
        if(newsRequestDto.getBody() != null) {
            Query query = QueryBuilders.match()
                    .query(newsRequestDto.getBody())
                    .field("body_text")
                    .build()._toQuery();
            mustList.add(query);
        }

        if (newsRequestDto.getFromTime() != null) {
            Query startedAtFilterQuery = QueryBuilders
                    .range()
                    .field("@timestamp")
                    .gte(JsonData.of(newsRequestDto.getFromTime()))
                    .build()._toQuery();
            filterList.add(startedAtFilterQuery);
        }
        if (newsRequestDto.getToTime() != null) {
            Query endedAtFilterQuery = QueryBuilders
                    .range()
                    .field("@timestamp")
                    .lt(JsonData.of(newsRequestDto.getToTime()))
                    .build()._toQuery();
            filterList.add(endedAtFilterQuery);
        }

        Query boolQuery = QueryBuilders.bool()
                .filter(filterList)
                .must(mustList)
                .build()._toQuery();

        return queryBuilder.withQuery(boolQuery)
                .build();
    }
}
