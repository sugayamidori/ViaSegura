package com.github.sugayamidori.viaseguraapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record PredictionDTO(
        String h3Cell,
        Date weekStart,
        BigDecimal predictedAccidents,
        LocalDateTime createdAt
) {
}
