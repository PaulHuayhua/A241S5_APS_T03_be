package com.agroapp.prediccion.catalogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agroapp.prediccion.catalogos.model.Variedad;

import java.util.List;

@Repository
public interface VariedadRepository extends JpaRepository<Variedad, Integer> {
    List<Variedad> findByCultivoIdCultivo(Integer idCultivo);
}
