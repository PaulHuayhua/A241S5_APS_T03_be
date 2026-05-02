package com.agroapp.prediccion.geografia.service.impl;

import com.agroapp.prediccion.geografia.model.Departamento;
import com.agroapp.prediccion.geografia.repository.DepartamentoRepository;
import com.agroapp.prediccion.geografia.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {
    
    private final DepartamentoRepository departamentoRepository;
    
    @Override
    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }
    
    @Override
    public Optional<Departamento> findById(Integer id) {
        return departamentoRepository.findById(id);
    }
    
    @Override
    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
    
    @Override
    public void deleteById(Integer id) {
        departamentoRepository.deleteById(id);
    }
}
