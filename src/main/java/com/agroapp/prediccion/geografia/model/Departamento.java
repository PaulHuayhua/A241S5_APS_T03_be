package com.agroapp.prediccion.geografia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "departamento")
public class Departamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Integer idDepartamento;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @Column(name = "codigo_ubigeo", nullable = false, unique = true, length = 10)
    private String codigoUbigeo;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
