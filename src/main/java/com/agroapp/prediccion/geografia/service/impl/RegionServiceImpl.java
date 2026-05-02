package com.agroapp.prediccion.geografia.service.impl;

import com.agroapp.prediccion.geografia.model.Region;
import com.agroapp.prediccion.geografia.repository.RegionRepository;
import com.agroapp.prediccion.geografia.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    
    private final RegionRepository regionRepository;
    
    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }
    
    @Override
    public Optional<Region> findById(Integer id) {
        return regionRepository.findById(id);
    }
    
    @Override
    public List<Region> findByDepartamento(Integer idDepartamento) {
        return regionRepository.findByDepartamentoIdDepartamento(idDepartamento);
    }
    
    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }
    
    @Override
    public void deleteById(Integer id) {
        regionRepository.deleteById(id);
    }
}
