package com.github.sugayamidori.viaseguraapi.service;

import com.github.sugayamidori.viaseguraapi.model.Heatmap;
import com.github.sugayamidori.viaseguraapi.repository.HeatmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.github.sugayamidori.viaseguraapi.repository.specs.HeatmapSpecs.*;

@Service
@RequiredArgsConstructor
public class HeatmapService {

    private final HeatmapRepository repository;

    public Page<Heatmap> search(
            String h3Cell,
            Integer year,
            Integer month,
            BigDecimal numCasualties,
            Integer page,
            Integer pageSize
    ) {

        Specification<Heatmap> specs = (root, query, cb) -> cb.conjunction();

        if(!h3Cell.isBlank()) {
            specs = specs.and(h3CellEquals(h3Cell));
        }

        if(year != null) {
            specs = specs.and(yearEquals(year));
        }

        if(month != null) {
            specs.and(monthEquals(month));
        }

        if(numCasualties != null) {
            specs = specs.and(numCasualtiesEquals(numCasualties));
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return repository.findAll(specs, pageRequest);
    }
}
