package com.agroapp.prediccion.geografia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agroapp.prediccion.geografia.model.Region;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findByDepartamentoIdDepartamento(Integer idDepartamento);
}
