package com.modoojeonse.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private String id;
    private LocalDateTime timestamp;
    private String title;
    private String body;
    private String author;
    private String address;
    private String addressDetail;
    private String contractType;
    private boolean isReturnDelayed;
    private Long deposit;
    private LocalDateTime contractDate;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long rating;
    private String img;
    private String searchAfter;
}
