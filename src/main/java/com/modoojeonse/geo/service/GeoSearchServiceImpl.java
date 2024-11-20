package com.modoojeonse.geo.service;

import com.modoojeonse.common.BusinessLogicException;
import com.modoojeonse.common.ExceptionCode;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoSearchServiceImpl implements GeoSearchService {
    private final GeoRepository geoRepository;
    private final GeoNativeQueryRepository geoNativeQueryRepository;

    @Override
    public List<GeoResponseDto> searchNative(GeoRequestDto geoRequestDto) {
        SearchHits<GeoDocument> searchHits = geoNativeQueryRepository.findByDistance(geoRequestDto);
        List<GeoResponseDto> searchResults = new ArrayList<>();
        for(var searchHit: searchHits){
            GeoResponseDto geoResponseDto = new GeoResponseDto(searchHit.getContent());
            searchResults.add(geoResponseDto);
        }
        return searchResults;
    }

    @Override
    public boolean saveGeo(GeoRequestDto geoRequestDto) throws Exception {
        checkDuplicatedGeo(geoRequestDto.getAddress());
        geoRepository.save(new GeoDocument(geoRequestDto));
        return true;
    }

    private void checkDuplicatedGeo(String address) {
        SearchHits<GeoDocument> searchHits = geoNativeQueryRepository.findByAddressKeyword(address);

        if (!searchHits.isEmpty()) {
            log.debug("GeoSearchServiceImpl.checkDuplicatedGeo exception occur address: {}", address);
            throw new BusinessLogicException(ExceptionCode.GEO_EXISTS);
        }
    }
}
