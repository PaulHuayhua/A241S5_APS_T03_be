package com.agroapp.prediccion.finanzas.repository;

import com.agroapp.prediccion.finanzas.model.CostoCampana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostoCampanaRepository extends JpaRepository<CostoCampana, Integer> {
    List<CostoCampana> findBySiembraIdSiembra(Integer idSiembra);
}
