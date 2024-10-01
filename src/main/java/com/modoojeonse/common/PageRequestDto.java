package com.modoojeonse.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRequestDto {
    private int page;
    private int size;

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1, size, sort);
    }
}
