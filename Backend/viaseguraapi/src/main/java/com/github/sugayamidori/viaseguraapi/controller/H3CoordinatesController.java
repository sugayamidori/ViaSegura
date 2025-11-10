package com.github.sugayamidori.viaseguraapi.controller;

import com.github.sugayamidori.viaseguraapi.controller.docs.H3CoordinatesControllerDocs;
import com.github.sugayamidori.viaseguraapi.controller.dto.H3CoordinatesDTO;
import com.github.sugayamidori.viaseguraapi.controller.mappers.H3CoordinatesMapper;
import com.github.sugayamidori.viaseguraapi.model.H3Coordinates;
import com.github.sugayamidori.viaseguraapi.service.H3CoordinatesService;
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
@RequestMapping("h3_grid")
@RequiredArgsConstructor
@Tag(name = "H3 Coordinates", description = "Endpoints for managing H3 Coordinates")
public class H3CoordinatesController implements H3CoordinatesControllerDocs {

    private final H3CoordinatesService service;
    private final H3CoordinatesMapper mapper;

    @GetMapping
    @Override
    public ResponseEntity<Page<H3CoordinatesDTO>> search(
            @RequestParam(value = "h3Cell", required = false)
            String h3Cell,
            @RequestParam(value = "latitude", required = false)
            BigDecimal latitude,
            @RequestParam(value = "longitude", required = false)
            BigDecimal longitude,
            @RequestParam(value = "bairro", required = false)
            String bairro,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "20")
            Integer tamanhoPagina

    ) {
        Page<H3Coordinates> paginaResultado = service.search(h3Cell, latitude, longitude, bairro, pagina, tamanhoPagina);

        Page<H3CoordinatesDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

}
