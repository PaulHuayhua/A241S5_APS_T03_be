package com.agroapp.prediccion.geografia.service;

import com.agroapp.prediccion.geografia.model.Departamento;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de departamentos
 */
public interface DepartamentoService {
    
    List<Departamento> findAll();
    
    Optional<Departamento> findById(Integer id);
    
    Departamento save(Departamento departamento);
    
    void deleteById(Integer id);
}
