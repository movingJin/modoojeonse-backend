package com.modoojeonse.news.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.modoojeonse.news.dto.NewsRequestDto;
import com.modoojeonse.news.entity.NewsDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsNativeQueryRepository {
    private final ElasticsearchOperations operations;

    public SearchHits<NewsDocument> findByNativeCondition(NewsRequestDto newsRequestDto) {
        Query query = createConditionNativeQuery(newsRequestDto);
        //System.out.println("testbug: " + query.getHighlightQuery());
        return operations.search(query, NewsDocument.class);
    }

    private NativeQuery createConditionNativeQuery(NewsRequestDto newsRequestDto) {
        NativeQueryBuilder queryBuilder = new NativeQueryBuilder();

        /*


        Query rangeQuery = (Query) QueryBuilders.range()
                .field("@timestamp")
                .gte(JsonData.of(LocalDateTime.now()
                        .truncatedTo(ChronoUnit.HOURS)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()))
                .lte(JsonData.of(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                .build()
                ._toQuery();

        return queryBuilder.withQuery(boolQuery)
                .withSourceFilter(sourceFilter)
                .withScriptedField(scriptedField)
                .withAggregation("top_ten", agg)
                .build();
         */

        Query matchQuery = (Query) QueryBuilders.match()
                .field("title")
                .query(newsRequestDto.getTitle())
                .build()
                ._toQuery();


        NativeQueryBuilder query = new NativeQueryBuilder();

        /*
        // BoolQueryBuilder mustBoolQueryBuilder = boolQuery();
        BoolQueryBuilder shouldBoolQueryBuilder = boolQuery();
        BoolQueryBuilder filterBoolQueryBuilder = boolQuery();

        if(newsRequestDto == null){
            return query.build();
        }

        if(newsRequestDto.getId() != null){
            filterBoolQueryBuilder.filter(matchQuery("id", newsRequestDto.getId()));
        }

        if(StringUtils.hasText(newsRequestDto.getTitle())){
            shouldBoolQueryBuilder.should(matchQuery("title", newsRequestDto.getTitle()));
        }

        if(StringUtils.hasText(newsRequestDto.getBody())){
            shouldBoolQueryBuilder.should(matchQuery("body_text", newsRequestDto.getBody()));
        }

        if(newsRequestDto.getFromTime() != null){
            filterBoolQueryBuilder.filter(rangeQuery("@timestamp").gte(newsRequestDto.getFromTime()));
        }

        if(newsRequestDto.getToTime() != null){
            filterBoolQueryBuilder.filter(rangeQuery("@timestamp").lt(newsRequestDto.getToTime()));
        }
        Query boolQuery = (Query) QueryBuilders.bool()
                .must(matchQuery)
                .build()
                ._toQuery();

        new NativeQueryBuilder().build();
        query.withQuery((co.elastic.clients.elasticsearch._types.query_dsl.Query) boolQuery)
                .withFilter(filterBoolQueryBuilder)
                .withSorts(SortBuilders.fieldSort("@timestamp")
                        .order(SortOrder.DESC));
*/
        return query.build();



    }
}
