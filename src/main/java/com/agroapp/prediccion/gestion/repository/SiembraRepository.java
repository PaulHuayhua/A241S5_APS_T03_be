package com.agroapp.prediccion.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agroapp.prediccion.gestion.model.Siembra;

import java.util.List;

@Repository
public interface SiembraRepository extends JpaRepository<Siembra, Integer> {
    List<Siembra> findByParcelaIdParcela(Integer idParcela);
    List<Siembra> findByUsuarioIdUsuario(Integer idUsuario);
}
