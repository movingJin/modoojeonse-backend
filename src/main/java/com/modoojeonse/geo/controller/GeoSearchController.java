package com.modoojeonse.geo.controller;

import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.geo.dto.GeoResponseDto;
import com.modoojeonse.geo.service.GeoSearchService;
import com.modoojeonse.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GeoSearchController {
    private final GeoSearchService geoSearchService;

    @ResponseBody
    @GetMapping("/geo/distance")
    private ResponseEntity< List<GeoResponseDto> > distance(@RequestParam Map<String, String> params) {
        double lat = Double.parseDouble(params.get("location.lat"));
        double lon = Double.parseDouble(params.get("location.lon"));
        GeoPoint geoPoint = new GeoPoint(lat, lon);
        GeoRequestDto request = new GeoRequestDto();
        request.setLocation(geoPoint);
        List<GeoResponseDto> locations = geoSearchService.searchNative(request);

        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/geo/save")
    public ResponseEntity<Void> save(@RequestBody GeoRequestDto request) throws Exception {
        geoSearchService.saveGeo(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
