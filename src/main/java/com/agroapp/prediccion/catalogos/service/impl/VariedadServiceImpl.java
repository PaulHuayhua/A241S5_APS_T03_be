package com.agroapp.prediccion.catalogos.service.impl;

import com.agroapp.prediccion.catalogos.model.Variedad;
import com.agroapp.prediccion.catalogos.repository.VariedadRepository;
import com.agroapp.prediccion.catalogos.service.VariedadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VariedadServiceImpl implements VariedadService {
    
    private final VariedadRepository variedadRepository;
    
    @Override
    public List<Variedad> findAll() {
        return variedadRepository.findAll();
    }
    
    @Override
    public List<Variedad> findAllIncludingInactive() {
        return variedadRepository.findAll();
    }
    
    @Override
    public Optional<Variedad> findById(Integer id) {
        return variedadRepository.findById(id);
    }
    
    @Override
    public List<Variedad> findByCultivo(Integer idCultivo) {
        return variedadRepository.findByCultivoIdCultivo(idCultivo);
    }
    
    @Override
    public Variedad save(Variedad variedad) {
        return variedadRepository.save(variedad);
    }
    
    @Override
    public void deleteById(Integer id) {
        variedadRepository.deleteById(id);
    }
}
