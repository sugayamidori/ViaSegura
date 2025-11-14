package com.github.sugayamidori.viaseguraapi.service;

import com.github.sugayamidori.viaseguraapi.model.H3Coordinates;
import com.github.sugayamidori.viaseguraapi.repository.H3CoordinatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.github.sugayamidori.viaseguraapi.repository.specs.H3CoordinatesSpecs.*;

@Service
@RequiredArgsConstructor
public class H3CoordinatesService {

    private final H3CoordinatesRepository repository;

    public Page<H3Coordinates> search(
            String h3Cell,
            BigDecimal latitude,
            BigDecimal longitude,
            String neighborhood,
            Integer page,
            Integer pageSize
    ) {

        Specification<H3Coordinates> specs = (root, query, cb) -> cb.conjunction();

        if(!h3Cell.isBlank()) {
            specs = specs.and(h3CellEquals(h3Cell));
        }

        if(latitude != null) {
            specs.and(latitudeEquals(latitude));
        }

        if(longitude != null) {
            specs = specs.and(longitudeEquals(longitude));
        }

        if(neighborhood != null) {
            specs = specs.and(neighborhoodLike(neighborhood));
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return repository.findAll(specs, pageRequest);
    }
}
