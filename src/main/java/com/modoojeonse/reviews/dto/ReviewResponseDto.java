package com.modoojeonse.reviews.dto;

import com.modoojeonse.reviews.entity.ReviewDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private String id;
    private LocalDateTime timestamp;
    private String title;
    private String body;
    private String author;
    private String address;
    private String contractType;
    private boolean isReturnDelayed;
    private Long deposit;
    private LocalDateTime contractDate;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long rating;
    private String img;
    private long sort;

    public ReviewResponseDto(ReviewDocument review) {
        this.id = review.getId();
        this.timestamp = review.getTimestamp();
        this.title = review.getTitle();
        this.body = review.getBody();
        this.author = review.getAuthor();
        this.address = review.getAddress();
        this.contractType = review.getContractType();
        this.isReturnDelayed = review.isReturnDelayed();
        this.deposit = review.getDeposit();
        this.contractDate = review.getContractDate();
        this.fromDate = review.getFromDate();
        this.toDate = review.getToDate();
        this.rating = review.getRating();
        this.img = review.getImg();
    }
}
