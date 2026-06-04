package com.agroapp.prediccion.seguridad.service.impl;

import com.agroapp.prediccion.seguridad.model.Usuario;
import com.agroapp.prediccion.seguridad.repository.UsuarioRepository;
import com.agroapp.prediccion.seguridad.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }
    
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
