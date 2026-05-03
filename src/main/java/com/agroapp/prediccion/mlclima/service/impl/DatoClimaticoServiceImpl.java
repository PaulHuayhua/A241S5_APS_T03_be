package com.agroapp.prediccion.mlclima.service.impl;

import com.agroapp.prediccion.mlclima.model.DatoClimatico;
import com.agroapp.prediccion.mlclima.repository.DatoClimaticoRepository;
import com.agroapp.prediccion.mlclima.service.DatoClimaticoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatoClimaticoServiceImpl implements DatoClimaticoService {
    
    private final DatoClimaticoRepository datoClimaticoRepository;
    
    @Override
    public List<DatoClimatico> findAll() {
        return datoClimaticoRepository.findAll();
    }
    
    @Override
    public Optional<DatoClimatico> findById(Integer id) {
        return datoClimaticoRepository.findById(id);
    }
    
    @Override
    public List<DatoClimatico> findByRegionAndFechas(Integer idRegion, LocalDate inicio, LocalDate fin) {
        return datoClimaticoRepository.findByRegionIdRegionAndFechaBetween(idRegion, inicio, fin);
    }
    
    @Override
    public DatoClimatico save(DatoClimatico datoClimatico) {
        return datoClimaticoRepository.save(datoClimatico);
    }
    
    @Override
    public void deleteById(Integer id) {
        datoClimaticoRepository.deleteById(id);
    }
}
