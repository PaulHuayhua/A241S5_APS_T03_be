package com.agroapp.prediccion.catalogos.repository;

import com.agroapp.prediccion.catalogos.model.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Integer> {
    
    // Buscar solo cultivos activos
    List<Cultivo> findByActivoTrue();
    
    // Buscar cultivo por ID y activo
    Optional<Cultivo> findByIdCultivoAndActivoTrue(Integer idCultivo);
    
    // Buscar por nombre (activos)
    List<Cultivo> findByNombreComunContainingIgnoreCaseAndActivoTrue(String nombreComun);
    
    // Buscar por tipo (activos)
    List<Cultivo> findByTipoCultivoAndActivoTrue(String tipoCultivo);
}
