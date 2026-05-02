package com.agroapp.prediccion.catalogos.service.impl;

import com.agroapp.prediccion.catalogos.repository.VariedadRepository;
import com.agroapp.prediccion.catalogos.model.Cultivo;
import com.agroapp.prediccion.catalogos.repository.CultivoRepository;
import com.agroapp.prediccion.catalogos.service.CultivoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CultivoServiceImpl implements CultivoService {
    
    private final CultivoRepository cultivoRepository;
    private final VariedadRepository variedadRepository;
    
    @Override
    public List<Cultivo> findAll() {
        return cultivoRepository.findByActivoTrue();
    }
    
    @Override
    public List<Cultivo> findAllIncludingInactive() {
        return cultivoRepository.findAll();
    }
    
    @Override
    public Optional<Cultivo> findById(Integer id) {
        return cultivoRepository.findByIdCultivoAndActivoTrue(id);
    }
    
    @Override
    public Optional<Cultivo> findByIdIncludingInactive(Integer id) {
        return cultivoRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Cultivo save(Cultivo cultivo) {
        // Si es nuevo, asegurar que esté activo
        if (cultivo.getIdCultivo() == null) {
            cultivo.setActivo(true);
            log.info("Creando nuevo cultivo: {}", cultivo.getNombreComun());
        } else {
            log.info("Actualizando cultivo ID {}: {}", cultivo.getIdCultivo(), cultivo.getNombreComun());
        }
        
        return cultivoRepository.save(cultivo);
    }
    
    @Override
    @Transactional
    public boolean deleteLogically(Integer id) {
        Optional<Cultivo> cultivoOpt = cultivoRepository.findById(id);
        
        if (cultivoOpt.isEmpty()) {
            log.warn("Intento de eliminar cultivo inexistente ID: {}", id);
            return false;
        }
        
        Cultivo cultivo = cultivoOpt.get();
        
        if (!cultivo.getActivo()) {
            log.warn("Cultivo ID {} ya está inactivo", id);
            return false;
        }
        
        // Marcar como inactivo
        cultivo.setActivo(false);
        cultivoRepository.save(cultivo);
        
        log.info("Cultivo ID {} marcado como inactivo: {}", id, cultivo.getNombreComun());
        return true;
    }
    
    @Override
    @Transactional
    public boolean reactivate(Integer id) {
        Optional<Cultivo> cultivoOpt = cultivoRepository.findById(id);
        
        if (cultivoOpt.isEmpty()) {
            log.warn("Intento de reactivar cultivo inexistente ID: {}", id);
            return false;
        }
        
        Cultivo cultivo = cultivoOpt.get();
        
        if (cultivo.getActivo()) {
            log.warn("Cultivo ID {} ya está activo", id);
            return false;
        }
        
        // Marcar como activo
        cultivo.setActivo(true);
        cultivoRepository.save(cultivo);
        
        log.info("Cultivo ID {} reactivado: {}", id, cultivo.getNombreComun());
        return true;
    }
    
    @Override
    @Transactional
    public void deletePhysically(Integer id) {
        log.warn("Eliminación física del cultivo ID: {}", id);
        cultivoRepository.deleteById(id);
    }
    
    @Override
    public boolean tieneRelaciones(Integer idCultivo) {
        long variedades = variedadRepository.findByCultivoIdCultivo(idCultivo).size();
        return variedades > 0;
    }
    
    @Override
    public List<Cultivo> findByNombreComun(String nombre) {
        return cultivoRepository.findByNombreComunContainingIgnoreCaseAndActivoTrue(nombre);
    }
    
    @Override
    public List<Cultivo> findByTipo(String tipo) {
        return cultivoRepository.findByTipoCultivoAndActivoTrue(tipo);
    }
}
