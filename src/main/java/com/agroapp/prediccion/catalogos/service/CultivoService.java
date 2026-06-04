package com.agroapp.prediccion.catalogos.service;

import com.agroapp.prediccion.catalogos.model.Cultivo;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de cultivos
 */
public interface CultivoService {

    List<Cultivo> findAll();

    List<Cultivo> findAllIncludingInactive();
    
    Optional<Cultivo> findById(Integer id);

    Optional<Cultivo> findByIdIncludingInactive(Integer id);
    
    Cultivo save(Cultivo cultivo);
    
    boolean deleteLogically(Integer id);
    
    boolean reactivate(Integer id);
    
    void deletePhysically(Integer id);

    boolean tieneRelaciones(Integer idCultivo);

    List<Cultivo> findByNombreComun(String nombre);
    
    List<Cultivo> findByTipo(String tipo);
}
