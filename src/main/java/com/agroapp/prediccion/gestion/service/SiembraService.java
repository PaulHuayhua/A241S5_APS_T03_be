package com.agroapp.prediccion.gestion.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.gestion.model.Siembra;

/**
 * Interfaz de servicio para la gestión de siembras
 */
public interface SiembraService {
    
    List<Siembra> findAll();
    
    Optional<Siembra> findById(Integer id);
    
    List<Siembra> findByParcela(Integer idParcela);
    
    List<Siembra> findByUsuario(Integer idUsuario);
    
    Siembra save(Siembra siembra);
    
    void deleteById(Integer id);
}
