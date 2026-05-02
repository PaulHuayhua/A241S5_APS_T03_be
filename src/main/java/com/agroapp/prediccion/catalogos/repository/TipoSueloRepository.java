package com.agroapp.prediccion.catalogos.repository;

import com.agroapp.prediccion.catalogos.model.TipoSuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSueloRepository extends JpaRepository<TipoSuelo, Integer> {
}
