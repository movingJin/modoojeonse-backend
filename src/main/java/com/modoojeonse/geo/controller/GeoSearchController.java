package com.modoojeonse.geo.controller;

import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.geo.dto.GeoResponseDto;
import com.modoojeonse.geo.service.GeoSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GeoSearchController {
    private final GeoSearchService geoSearchService;

    @ResponseBody
    @GetMapping("/geo/distance")
    private ResponseEntity< List<GeoResponseDto> > distance(@RequestBody GeoRequestDto request) {
        List<GeoResponseDto> locations = geoSearchService.searchNative(request);

        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
}
