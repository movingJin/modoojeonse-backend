package com.modoojeonse.geo.dto;

import com.modoojeonse.geo.entity.GeoDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoResponseDto {
    private String id;
    private LocalDateTime timestamp;
    private GeoPoint location;
    private String address;
    private String author;

    public GeoResponseDto(GeoDocument location) {
        this.id = location.getId();
        this.timestamp = location.getTimestamp();
        this.location = location.getLocation();
        this.address = location.getAddress();
        this.author = location.getAuthor();
    }
}
