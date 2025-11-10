package com.github.sugayamidori.viaseguraapi.repository.specs;

import com.github.sugayamidori.viaseguraapi.model.Heatmap;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class HeatmapSpecs {

    public static Specification<Heatmap> h3CellEquals(String h3Cell) {
        return (root, query, cb) -> cb.equal(root.get("h3Cell"), h3Cell);
    }

    public static Specification<Heatmap> yearEquals(Integer year) {
        return (root, query, cb) -> cb.equal(root.get("year"), year);
    }

    public static Specification<Heatmap> monthEquals(Integer month) {
        return (root, query, cb) -> cb.equal(root.get("month"), month);
    }

    public static Specification<Heatmap> numSinistrosEquals(BigDecimal numSinistros) {
        return (root, query, cb) -> cb.equal(root.get("numSinistros"), numSinistros);
    }
}
