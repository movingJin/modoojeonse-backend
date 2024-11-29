package com.modoojeonse.reviews.entity;

import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.reviews.dto.ReviewRequestDto;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

@Getter
@Document(indexName = "modoojeonse-reviews-#{@elasticIndex.getIndexYear()}")
@Setting(replicas = 0)
public class ReviewDocument {
    @Id
    private String id;

    @Field(name = "@timestamp", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX", format = {})
    private LocalDateTime timestamp;

    @Field(type = FieldType.Text)
    private String title;

    @Field(name="body_text", type = FieldType.Text, analyzer = "search_nori_analyzer")
    private String body;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String author;

    @Field(type = FieldType.Keyword)
    private String address;

    @Field(name="address_detail", type = FieldType.Text)
    private String addressDetail;

    @Field(name="contract_type", type = FieldType.Keyword)
    private String contractType;

    @Field(name="is_return_delayed", type = FieldType.Boolean)
    private boolean isReturnDelayed;

    @Field(type = FieldType.Long)
    private Long deposit;

    @Field(name = "contract_date", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm", format = {})
    private LocalDateTime contractDate;

    @Field(name = "from_date", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm", format = {})
    private LocalDateTime fromDate;

    @Field(name = "to_date", type = FieldType.Date, pattern = "uuuu-MM-dd'T'HH:mm", format = {})
    private LocalDateTime toDate;

    @Field(type = FieldType.Integer)
    private Long rating;

    @Field(type = FieldType.Text)
    private String img;

    public ReviewDocument() {};

    public ReviewDocument(ReviewRequestDto reviewRequestDto) {
        this.id = reviewRequestDto.getId();
        this.timestamp = reviewRequestDto.getTimestamp();
        this.title = reviewRequestDto.getTitle();
        this.body = reviewRequestDto.getBody();
        this.address = reviewRequestDto.getAddress();
        this.addressDetail = reviewRequestDto.getAddressDetail().strip();
        this.author = reviewRequestDto.getAuthor();
        this.contractType = reviewRequestDto.getContractType();
        this.isReturnDelayed = reviewRequestDto.isReturnDelayed();
        this.deposit = reviewRequestDto.getDeposit();
        this.contractDate = reviewRequestDto.getContractDate();
        this.fromDate = reviewRequestDto.getFromDate();
        this.toDate = reviewRequestDto.getToDate();
        this.rating = reviewRequestDto.getRating();
        this.img = reviewRequestDto.getImg();
    }
}
