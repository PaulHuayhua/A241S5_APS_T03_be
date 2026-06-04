package com.agroapp.prediccion.catalogos.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.catalogos.model.Variedad;


public interface VariedadService {
    
    List<Variedad> findAll();
    
    List<Variedad> findAllIncludingInactive();
    
    Optional<Variedad> findById(Integer id);
    
    List<Variedad> findByCultivo(Integer idCultivo);
    
    Variedad save(Variedad variedad);
    
    void deleteById(Integer id);
}
