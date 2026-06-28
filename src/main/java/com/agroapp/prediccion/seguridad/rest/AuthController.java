package com.agroapp.prediccion.seguridad.rest;

import com.agroapp.prediccion.seguridad.dto.LoginRequest;
import com.agroapp.prediccion.seguridad.dto.LoginResponse;
import com.agroapp.prediccion.seguridad.model.Usuario;
import com.agroapp.prediccion.seguridad.repository.UsuarioRepository;
import com.agroapp.prediccion.seguridad.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElse(null);

        // Validar existencia, estado activo y contraseña
        if (usuario == null || !usuario.getActivo()
                || !passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Actualizar último acceso
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().getNombreRol());

        return ResponseEntity.ok(new LoginResponse(
                token,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombreRol()
        ));
    }
}
