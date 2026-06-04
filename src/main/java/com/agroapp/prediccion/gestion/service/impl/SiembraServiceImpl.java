package com.agroapp.prediccion.gestion.service.impl;

import com.agroapp.prediccion.gestion.model.Siembra;
import com.agroapp.prediccion.gestion.repository.SiembraRepository;
import com.agroapp.prediccion.gestion.service.SiembraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiembraServiceImpl implements SiembraService {
    
    private final SiembraRepository siembraRepository;
    
    @Override
    public List<Siembra> findAll() {
        return siembraRepository.findAll();
    }
    
    @Override
    public Optional<Siembra> findById(Integer id) {
        return siembraRepository.findById(id);
    }
    
    @Override
    public List<Siembra> findByParcela(Integer idParcela) {
        return siembraRepository.findByParcelaIdParcela(idParcela);
    }
    
    @Override
    public List<Siembra> findByUsuario(Integer idUsuario) {
        return siembraRepository.findByUsuarioIdUsuario(idUsuario);
    }
    
    @Override
    public Siembra save(Siembra siembra) {
        return siembraRepository.save(siembra);
    }
    
    @Override
    public void deleteById(Integer id) {
        siembraRepository.deleteById(id);
    }
}
