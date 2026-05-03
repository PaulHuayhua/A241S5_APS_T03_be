package com.agroapp.prediccion.mlclima.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.mlclima.model.ModeloML;

public interface ModeloMLService {
    List<ModeloML> findAll();
    Optional<ModeloML> findById(Integer id);
    Optional<ModeloML> findByNombre(String nombreModelo);
    ModeloML save(ModeloML modelo);
    void deleteById(Integer id);
}
