package com.modoojeonse.news.dto;

import com.modoojeonse.news.entity.NewsDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseDto {
    private String id;
    private LocalDateTime timestamp;
    private String title;
    private String summary;
    private String body;
    private String html;
    private String publisher;
    private String author;
    private String url;

    public NewsResponseDto(NewsDocument news) {
        this.id = news.getId();
        this.timestamp = news.getTimestamp();
        this.title = news.getTitle();
        this.summary = news.getSummary();
        this.body = news.getBody();
        this.html = news.getHtml();
        this.publisher = news.getPublisher();
        this.author = news.getAuthor();
        this.url = news.getUrl();
    }
}
