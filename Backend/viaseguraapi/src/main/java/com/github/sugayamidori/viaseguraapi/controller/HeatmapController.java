package com.github.sugayamidori.viaseguraapi.controller;

import com.github.sugayamidori.viaseguraapi.controller.docs.HeatmapControllerDocs;
import com.github.sugayamidori.viaseguraapi.controller.dto.HeatmapDTO;
import com.github.sugayamidori.viaseguraapi.controller.mappers.HeatmapMapper;
import com.github.sugayamidori.viaseguraapi.model.Heatmap;
import com.github.sugayamidori.viaseguraapi.service.HeatmapService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("heatmap")
@RequiredArgsConstructor
@Tag(name = "Heatmap", description = "Endpoints for managing heatmap")
public class HeatmapController implements HeatmapControllerDocs {

    private final HeatmapService service;
    private final HeatmapMapper mapper;

    @GetMapping
    @Override
    public ResponseEntity<Page<HeatmapDTO>> search(
            @RequestParam(value = "h3Cell", required = false)
            String h3Cell,
            @RequestParam(value = "year", required = false)
            Integer year,
            @RequestParam(value = "month", required = false)
            Integer month,
            @RequestParam(value = "numSinistros", required = false)
            BigDecimal numSinistros,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "20")
            Integer tamanhoPagina

    ) {
        Page<Heatmap> paginaResultado = service.search(h3Cell, year, month, numSinistros, pagina, tamanhoPagina);

        Page<HeatmapDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

}
