package com.agroapp.prediccion.seguridad.service;

import com.agroapp.prediccion.seguridad.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    
    List<Rol> findAll();
    
    Optional<Rol> findById(Integer id);
    
    Optional<Rol> findByNombre(String nombreRol);
    
    Rol save(Rol rol);
    
    void deleteById(Integer id);
}
