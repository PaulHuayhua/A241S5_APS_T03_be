package com.agroapp.prediccion.gestion.service.impl;

import com.agroapp.prediccion.gestion.model.Parcela;
import com.agroapp.prediccion.gestion.repository.ParcelaRepository;
import com.agroapp.prediccion.gestion.service.ParcelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParcelaServiceImpl implements ParcelaService {
    
    private final ParcelaRepository parcelaRepository;
    
    @Override
    public List<Parcela> findAll() {
        return parcelaRepository.findAll();
    }
    
    @Override
    public Optional<Parcela> findById(Integer id) {
        return parcelaRepository.findById(id);
    }
    
    @Override
    public List<Parcela> findByUsuario(Integer idUsuario) {
        return parcelaRepository.findByUsuarioIdUsuario(idUsuario);
    }
    
    @Override
    public Parcela save(Parcela parcela) {
        return parcelaRepository.save(parcela);
    }
    
    @Override
    public void deleteById(Integer id) {
        parcelaRepository.deleteById(id);
    }
}
