package com.modoojeonse.news.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "modoojeonse-news")
@Setting(replicas = 0)
public class NewsDocument {
    @Id
    private String id;

    @Field(name = "@timestamp", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX", format = {})

    private LocalDateTime timestamp;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text, analyzer = "search_nori_analyzer")
    private String summary_text;

    @Field(name="body_text", type = FieldType.Text, analyzer = "search_nori_analyzer")
    private String body;

    @Field(type = FieldType.Text, index = false, docValues = false)
    private String html;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String publisher;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String author;

    @Field(type = FieldType.Text, index = false, docValues = false)
    private String url;
}
