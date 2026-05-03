package com.agroapp.prediccion.finanzas.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.finanzas.model.AlertaClimatica;

public interface AlertaClimaticaService {
    List<AlertaClimatica> findAll();
    Optional<AlertaClimatica> findById(Integer id);
    List<AlertaClimatica> findByRegionActivas(Integer idRegion);
    AlertaClimatica save(AlertaClimatica alerta);
    void deleteById(Integer id);
}
