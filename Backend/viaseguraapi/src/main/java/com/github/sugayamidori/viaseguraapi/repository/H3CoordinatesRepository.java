package com.github.sugayamidori.viaseguraapi.repository;

import com.github.sugayamidori.viaseguraapi.model.H3Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface H3CoordinatesRepository extends JpaRepository<H3Coordinates, UUID>, JpaSpecificationExecutor<H3Coordinates> {
}
