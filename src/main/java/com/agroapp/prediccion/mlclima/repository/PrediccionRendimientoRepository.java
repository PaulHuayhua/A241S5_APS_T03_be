package com.agroapp.prediccion.mlclima.repository;

import com.agroapp.prediccion.mlclima.model.PrediccionRendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrediccionRendimientoRepository extends JpaRepository<PrediccionRendimiento, Integer> {
    List<PrediccionRendimiento> findBySiembraIdSiembra(Integer idSiembra);
}
