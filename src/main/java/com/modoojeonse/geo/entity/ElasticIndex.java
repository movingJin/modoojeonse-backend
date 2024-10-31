package com.modoojeonse.geo.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ElasticIndex {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public int getIndexDate() {
        return LocalDate.now().getYear();
    }
}
