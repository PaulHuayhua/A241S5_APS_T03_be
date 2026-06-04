package com.agroapp.prediccion.seguridad.service.impl;

import com.agroapp.prediccion.seguridad.model.Auditoria;
import com.agroapp.prediccion.seguridad.repository.AuditoriaRepository;
import com.agroapp.prediccion.seguridad.service.AuditoriaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {
    
    private final AuditoriaRepository auditoriaRepository;
    
    @Override
    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
    }
    
    @Override
    public Optional<Auditoria> findById(Integer id) {
        return auditoriaRepository.findById(id);
    }
    
    @Override
    public Auditoria save(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }
    
    @Override
    public void deleteById(Integer id) {
        auditoriaRepository.deleteById(id);
    }
}
