package com.agroapp.prediccion.gestion.service;

import com.agroapp.prediccion.gestion.model.Parcela;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de parcelas
 */
public interface ParcelaService {
    
    List<Parcela> findAll();
    
    Optional<Parcela> findById(Integer id);
    
    List<Parcela> findByUsuario(Integer idUsuario);
    
    Parcela save(Parcela parcela);
    
    void deleteById(Integer id);
}
