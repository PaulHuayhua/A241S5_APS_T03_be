package com.agroapp.prediccion.seguridad.service.impl;

import com.agroapp.prediccion.seguridad.model.Rol;
import com.agroapp.prediccion.seguridad.repository.RolRepository;
import com.agroapp.prediccion.seguridad.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {
    
    private final RolRepository rolRepository;
    
    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    
    @Override
    public Optional<Rol> findById(Integer id) {
        return rolRepository.findById(id);
    }
    
    @Override
    public Optional<Rol> findByNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }
    
    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }
    
    @Override
    public void deleteById(Integer id) {
        rolRepository.deleteById(id);
    }
}
