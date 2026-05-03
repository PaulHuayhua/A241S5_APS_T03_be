package com.agroapp.prediccion.mlclima.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agroapp.prediccion.mlclima.model.ModeloML;

import java.util.Optional;

@Repository
public interface ModeloMLRepository extends JpaRepository<ModeloML, Integer> {
    Optional<ModeloML> findByNombreModelo(String nombreModelo);
}
