package com.agroapp.prediccion.gestion.service;

import com.agroapp.prediccion.gestion.model.Cosecha;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de cosechas
 */
public interface CosechaService {
    
    List<Cosecha> findAll();
    
    Optional<Cosecha> findById(Integer id);
    
    List<Cosecha> findBySiembra(Integer idSiembra);
    
    Cosecha save(Cosecha cosecha);
    
    void deleteById(Integer id);
}
