package com.modoojeonse.geo.entity;

import com.modoojeonse.geo.dto.GeoRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "modoojeonse-geo-#{@elasticIndex.getIndexYear()}")
@Setting(replicas = 0)

public class GeoDocument {
    @Id
    private String id;

    @Field(name = "@timestamp", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX", format = {})
    private LocalDateTime timestamp;

    @Field(name = "location")
    private GeoPoint location;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String author;

    @Field(type = FieldType.Keyword)
    private String type;

    public GeoDocument() {} //nativeSearch 할 때, 사용 됨.

    public GeoDocument(GeoRequestDto geoRequestDto) {
        this.id = geoRequestDto.getId();
        this.location = geoRequestDto.getLocation();
        this.address = geoRequestDto.getAddress();
        this.author = geoRequestDto.getAuthor();
        this.type = geoRequestDto.getType();
    }
}
