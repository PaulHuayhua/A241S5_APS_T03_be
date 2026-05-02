package com.agroapp.prediccion.catalogos.service.impl;

import com.agroapp.prediccion.catalogos.repository.TipoSueloRepository;
import com.agroapp.prediccion.catalogos.service.TipoSueloService;
import com.agroapp.prediccion.catalogos.model.TipoSuelo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoSueloServiceImpl implements TipoSueloService {
    
    private final TipoSueloRepository tipoSueloRepository;
    
    @Override
    public List<TipoSuelo> findAll() {
        return tipoSueloRepository.findAll();
    }
    
    @Override
    public Optional<TipoSuelo> findById(Integer id) {
        return tipoSueloRepository.findById(id);
    }
    
    @Override
    public TipoSuelo save(TipoSuelo tipoSuelo) {
        return tipoSueloRepository.save(tipoSuelo);
    }
    
    @Override
    public void deleteById(Integer id) {
        tipoSueloRepository.deleteById(id);
    }
}
