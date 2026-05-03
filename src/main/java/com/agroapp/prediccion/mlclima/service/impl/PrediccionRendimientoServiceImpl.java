package com.agroapp.prediccion.mlclima.service.impl;

import com.agroapp.prediccion.mlclima.repository.PrediccionRendimientoRepository;
import com.agroapp.prediccion.mlclima.service.PrediccionRendimientoService;
import com.agroapp.prediccion.mlclima.model.PrediccionRendimiento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrediccionRendimientoServiceImpl implements PrediccionRendimientoService {
    
    private final PrediccionRendimientoRepository prediccionRepository;
    
    @Override
    public List<PrediccionRendimiento> findAll() {
        return prediccionRepository.findAll();
    }
    
    @Override
    public Optional<PrediccionRendimiento> findById(Integer id) {
        return prediccionRepository.findById(id);
    }
    
    @Override
    public List<PrediccionRendimiento> findBySiembra(Integer idSiembra) {
        return prediccionRepository.findBySiembraIdSiembra(idSiembra);
    }
    
    @Override
    public PrediccionRendimiento save(PrediccionRendimiento prediccion) {
        return prediccionRepository.save(prediccion);
    }
    
    @Override
    public void deleteById(Integer id) {
        prediccionRepository.deleteById(id);
    }
}
