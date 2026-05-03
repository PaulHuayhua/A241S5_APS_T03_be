package com.agroapp.prediccion.finanzas.service.impl;

import com.agroapp.prediccion.finanzas.model.AlertaClimatica;
import com.agroapp.prediccion.finanzas.repository.AlertaClimaticaRepository;
import com.agroapp.prediccion.finanzas.service.AlertaClimaticaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertaClimaticaServiceImpl implements AlertaClimaticaService {
    
    private final AlertaClimaticaRepository alertaRepository;
    
    @Override
    public List<AlertaClimatica> findAll() {
        return alertaRepository.findAll();
    }
    
    @Override
    public Optional<AlertaClimatica> findById(Integer id) {
        return alertaRepository.findById(id);
    }
    
    @Override
    public List<AlertaClimatica> findByRegionActivas(Integer idRegion) {
        return alertaRepository.findByRegionIdRegionAndActivaTrue(idRegion);
    }
    
    @Override
    public AlertaClimatica save(AlertaClimatica alerta) {
        return alertaRepository.save(alerta);
    }
    
    @Override
    public void deleteById(Integer id) {
        alertaRepository.deleteById(id);
    }
}
