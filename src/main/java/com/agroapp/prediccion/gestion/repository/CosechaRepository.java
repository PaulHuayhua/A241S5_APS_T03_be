package com.agroapp.prediccion.gestion.repository;

import com.agroapp.prediccion.gestion.model.Cosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CosechaRepository extends JpaRepository<Cosecha, Integer> {
    List<Cosecha> findBySiembraIdSiembra(Integer idSiembra);
}
