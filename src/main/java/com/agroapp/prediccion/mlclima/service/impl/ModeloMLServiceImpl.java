package com.agroapp.prediccion.mlclima.service.impl;

import com.agroapp.prediccion.mlclima.model.ModeloML;
import com.agroapp.prediccion.mlclima.repository.ModeloMLRepository;
import com.agroapp.prediccion.mlclima.service.ModeloMLService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModeloMLServiceImpl implements ModeloMLService {
    
    private final ModeloMLRepository modeloMLRepository;
    
    @Override
    public List<ModeloML> findAll() {
        return modeloMLRepository.findAll();
    }
    
    @Override
    public Optional<ModeloML> findById(Integer id) {
        return modeloMLRepository.findById(id);
    }
    
    @Override
    public Optional<ModeloML> findByNombre(String nombreModelo) {
        return modeloMLRepository.findByNombreModelo(nombreModelo);
    }
    
    @Override
    public ModeloML save(ModeloML modelo) {
        return modeloMLRepository.save(modelo);
    }
    
    @Override
    public void deleteById(Integer id) {
        modeloMLRepository.deleteById(id);
    }
}
