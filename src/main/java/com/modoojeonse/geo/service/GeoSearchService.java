package com.modoojeonse.geo.service;

import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.geo.dto.GeoResponseDto;

import java.util.List;

public interface GeoSearchService {
    public List<GeoResponseDto> searchNative(GeoRequestDto geoRequestDto);
    public boolean saveGeo(GeoRequestDto geoRequestDto) throws Exception;
}
