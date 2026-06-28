package com.agroapp.prediccion.seguridad.dto;

public record LoginResponse(
        String token,
        String nombre,
        String email,
        String rol
) {}
