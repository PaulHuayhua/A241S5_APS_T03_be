package com.agroapp.prediccion.catalogos.service;

import com.agroapp.prediccion.catalogos.model.Cultivo;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de cultivos
 */
public interface CultivoService {
    
    /**
     * Obtiene todos los cultivos activos
     */
    List<Cultivo> findAll();
    
    /**
     * Obtiene todos los cultivos (activos e inactivos)
     */
    List<Cultivo> findAllIncludingInactive();
    
    /**
     * Busca un cultivo por ID (solo si está activo)
     */
    Optional<Cultivo> findById(Integer id);
    
    /**
     * Busca un cultivo por ID (incluyendo inactivos)
     */
    Optional<Cultivo> findByIdIncludingInactive(Integer id);
    
    /**
     * Crea o actualiza un cultivo
     */
    Cultivo save(Cultivo cultivo);
    
    /**
     * Eliminación lógica de un cultivo
     * @return true si se eliminó correctamente, false si ya estaba inactivo
     */
    boolean deleteLogically(Integer id);
    
    /**
     * Reactiva un cultivo previamente eliminado
     * @return true si se reactivó correctamente, false si ya estaba activo
     */
    boolean reactivate(Integer id);
    
    /**
     * Eliminación física (solo para casos especiales)
     */
    void deletePhysically(Integer id);
    
    /**
     * Verifica si el cultivo tiene relaciones con otras entidades
     */
    boolean tieneRelaciones(Integer idCultivo);
    
    /**
     * Busca cultivos por nombre
     */
    List<Cultivo> findByNombreComun(String nombre);
    
    /**
     * Busca cultivos por tipo
     */
    List<Cultivo> findByTipo(String tipo);
}
