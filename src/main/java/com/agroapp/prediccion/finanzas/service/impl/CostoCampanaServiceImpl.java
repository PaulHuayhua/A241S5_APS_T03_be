package com.agroapp.prediccion.finanzas.service.impl;

import com.agroapp.prediccion.finanzas.service.CostoCampanaService;
import com.agroapp.prediccion.finanzas.model.CostoCampana;
import com.agroapp.prediccion.finanzas.repository.CostoCampanaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CostoCampanaServiceImpl implements CostoCampanaService {
    
    private final CostoCampanaRepository costoCampanaRepository;
    
    @Override
    public List<CostoCampana> findAll() {
        return costoCampanaRepository.findAll();
    }
    
    @Override
    public Optional<CostoCampana> findById(Integer id) {
        return costoCampanaRepository.findById(id);
    }
    
    @Override
    public List<CostoCampana> findBySiembra(Integer idSiembra) {
        return costoCampanaRepository.findBySiembraIdSiembra(idSiembra);
    }
    
    @Override
    public CostoCampana save(CostoCampana costo) {
        return costoCampanaRepository.save(costo);
    }
    
    @Override
    public void deleteById(Integer id) {
        costoCampanaRepository.deleteById(id);
    }
}
