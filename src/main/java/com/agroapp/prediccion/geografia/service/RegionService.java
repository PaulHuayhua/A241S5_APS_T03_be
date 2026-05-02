package com.agroapp.prediccion.geografia.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.geografia.model.Region;

public interface RegionService {
    
    List<Region> findAll();
    
    Optional<Region> findById(Integer id);
    
    List<Region> findByDepartamento(Integer idDepartamento);
    
    Region save(Region region);
    
    void deleteById(Integer id);
}
