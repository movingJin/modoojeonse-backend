package com.modoojeonse.geo.service;

import com.modoojeonse.geo.dto.GeoRequestDto;
import com.modoojeonse.geo.dto.GeoResponseDto;
import com.modoojeonse.geo.entity.GeoDocument;
import com.modoojeonse.geo.repository.GeoNativeQueryRepository;
import com.modoojeonse.geo.repository.GeoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoSearchServiceImpl implements GeoSearchService {
    private final GeoRepository geoRepository;
    private final GeoNativeQueryRepository geoNativeQueryRepository;

    @Override
    public List<GeoResponseDto> searchNative(GeoRequestDto geoRequestDto) {
        SearchHits<GeoDocument> searchHits = geoNativeQueryRepository.findByNativeCondition(geoRequestDto);
        List<GeoResponseDto> searchResults = new ArrayList<>();
        for(var searchHit: searchHits){
            GeoResponseDto geoResponseDto = new GeoResponseDto(searchHit.getContent());
            searchResults.add(geoResponseDto);
        }
        return searchResults;
    }

    public void saveGeo(GeoRequestDto geoRequestDto){
        geoRepository.save(new GeoDocument(geoRequestDto));
    }
}
