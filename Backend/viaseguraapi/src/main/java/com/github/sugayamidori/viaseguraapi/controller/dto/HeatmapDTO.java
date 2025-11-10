package com.github.sugayamidori.viaseguraapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HeatmapDTO(
        String h3Cell,
        Integer year,
        Integer month,
        BigDecimal numSinistros,
        LocalDateTime createdAt
) {
}
