package com.modoojeonse.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestDto {
    private String id;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private String title;
    private String body;
    private String publisher;
    private String author;
    private String url;
}
