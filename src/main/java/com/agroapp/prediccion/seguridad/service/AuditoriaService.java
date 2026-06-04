package com.agroapp.prediccion.seguridad.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.seguridad.model.Auditoria;

public interface AuditoriaService {
    List<Auditoria> findAll();
    Optional<Auditoria> findById(Integer id);
    Auditoria save(Auditoria auditoria);
    void deleteById(Integer id);
}
