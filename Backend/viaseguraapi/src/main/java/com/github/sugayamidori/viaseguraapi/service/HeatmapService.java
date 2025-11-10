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
            BigDecimal numSinistros,
            Integer pagina,
            Integer tamanhoPagina
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

        if(numSinistros != null) {
            specs = specs.and(numSinistrosEquals(numSinistros));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }
}
