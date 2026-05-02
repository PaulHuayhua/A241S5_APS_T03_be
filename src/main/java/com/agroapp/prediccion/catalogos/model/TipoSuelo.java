package com.agroapp.prediccion.catalogos.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_suelo")
public class TipoSuelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_suelo")
    private Integer idTipoSuelo;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
    
    @Column(name = "clasificacion", length = 100)
    private String clasificacion;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
