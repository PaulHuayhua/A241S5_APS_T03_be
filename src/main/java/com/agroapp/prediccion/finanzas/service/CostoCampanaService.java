package com.agroapp.prediccion.finanzas.service;

import com.agroapp.prediccion.finanzas.model.CostoCampana;

import java.util.List;
import java.util.Optional;

public interface CostoCampanaService {
    List<CostoCampana> findAll();
    Optional<CostoCampana> findById(Integer id);
    List<CostoCampana> findBySiembra(Integer idSiembra);
    CostoCampana save(CostoCampana costo);
    void deleteById(Integer id);
}
