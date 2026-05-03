package com.agroapp.prediccion.mlclima.repository;

import com.agroapp.prediccion.mlclima.model.DatoClimatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DatoClimaticoRepository extends JpaRepository<DatoClimatico, Integer> {
    List<DatoClimatico> findByRegionIdRegionAndFechaBetween(Integer idRegion, LocalDate inicio, LocalDate fin);
}
