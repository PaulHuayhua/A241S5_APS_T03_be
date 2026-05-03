package com.agroapp.prediccion.mlclima.service;

import com.agroapp.prediccion.mlclima.model.PrediccionRendimiento;

import java.util.List;
import java.util.Optional;

public interface PrediccionRendimientoService {
    List<PrediccionRendimiento> findAll();
    Optional<PrediccionRendimiento> findById(Integer id);
    List<PrediccionRendimiento> findBySiembra(Integer idSiembra);
    PrediccionRendimiento save(PrediccionRendimiento prediccion);
    void deleteById(Integer id);
}
