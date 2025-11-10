package com.github.sugayamidori.viaseguraapi.controller.mappers;

import com.github.sugayamidori.viaseguraapi.controller.dto.H3CoordinatesDTO;
import com.github.sugayamidori.viaseguraapi.model.H3Coordinates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface H3CoordinatesMapper {

    H3Coordinates toEntity(H3CoordinatesDTO dto);

    H3CoordinatesDTO toDTO(H3Coordinates heatmap);
}
