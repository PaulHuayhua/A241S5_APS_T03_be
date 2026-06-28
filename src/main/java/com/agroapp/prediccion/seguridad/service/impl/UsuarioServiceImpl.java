package com.agroapp.prediccion.seguridad.service.impl;

import com.agroapp.prediccion.seguridad.model.Usuario;
import com.agroapp.prediccion.seguridad.repository.UsuarioRepository;
import com.agroapp.prediccion.seguridad.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
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
        // Si el password no está hasheado aún (no empieza con el prefijo BCrypt), lo hashea
        if (usuario.getPasswordHash() != null && !usuario.getPasswordHash().startsWith("$2a$")) {
            usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        }
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
