package com.agroapp.prediccion.catalogos.service;

import com.agroapp.prediccion.catalogos.model.TipoSuelo;

import java.util.List;
import java.util.Optional;

public interface TipoSueloService {
    
    List<TipoSuelo> findAll();
    
    Optional<TipoSuelo> findById(Integer id);
    
    TipoSuelo save(TipoSuelo tipoSuelo);
    
    void deleteById(Integer id);
}
