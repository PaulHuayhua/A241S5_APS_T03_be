package com.agroapp.prediccion.mlclima.service;

import com.agroapp.prediccion.mlclima.model.DatoClimatico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DatoClimaticoService {
    List<DatoClimatico> findAll();
    Optional<DatoClimatico> findById(Integer id);
    List<DatoClimatico> findByRegionAndFechas(Integer idRegion, LocalDate inicio, LocalDate fin);
    DatoClimatico save(DatoClimatico datoClimatico);
    void deleteById(Integer id);
}
