package com.agroapp.prediccion.gestion.repository;

import com.agroapp.prediccion.gestion.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Integer> {
    List<Parcela> findByUsuarioIdUsuario(Integer idUsuario);
}
