package com.agroapp.prediccion.finanzas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agroapp.prediccion.finanzas.model.AlertaClimatica;

import java.util.List;

@Repository
public interface AlertaClimaticaRepository extends JpaRepository<AlertaClimatica, Integer> {
    List<AlertaClimatica> findByRegionIdRegionAndActivaTrue(Integer idRegion);
}
