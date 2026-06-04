package com.agroapp.prediccion.gestion.service.impl;

import com.agroapp.prediccion.gestion.repository.CosechaRepository;
import com.agroapp.prediccion.gestion.service.CosechaService;
import com.agroapp.prediccion.gestion.model.Cosecha;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CosechaServiceImpl implements CosechaService {
    
    private final CosechaRepository cosechaRepository;
    
    @Override
    public List<Cosecha> findAll() {
        return cosechaRepository.findAll();
    }
    
    @Override
    public Optional<Cosecha> findById(Integer id) {
        return cosechaRepository.findById(id);
    }
    
    @Override
    public List<Cosecha> findBySiembra(Integer idSiembra) {
        return cosechaRepository.findBySiembraIdSiembra(idSiembra);
    }
    
    @Override
    public Cosecha save(Cosecha cosecha) {
        return cosechaRepository.save(cosecha);
    }
    
    @Override
    public void deleteById(Integer id) {
        cosechaRepository.deleteById(id);
    }
}
