package com.agroapp.prediccion.seguridad.service;

import java.util.List;
import java.util.Optional;

import com.agroapp.prediccion.seguridad.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findByEmail(String email);
    Usuario save(Usuario usuario);
    void deleteById(Integer id);
}
