package com.agroapp.prediccion.seguridad.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Table(name = "rol")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;
    
    @Column(name = "nombre_rol", nullable = false, unique = true, length = 50)
    private String nombreRol;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "permisos", nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> permisos;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
