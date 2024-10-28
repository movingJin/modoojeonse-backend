package com.modoojeonse.geo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "modoojeonse-geo")
@Setting(replicas = 0)
public class GeoDocument {
    @Id
    private String id;

    @Field(name = "@timestamp", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX", format = {})
    private LocalDateTime timestamp;

    @Field(name = "location")
    private GeoPoint location;

    @Field(type = FieldType.Text, analyzer = "search_nori_analyzer")
    private String address;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String author;
}
