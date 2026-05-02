package com.agroapp.prediccion.geografia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "region")
public class Region {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Integer idRegion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_departamento", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Departamento departamento;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;
    
    @Column(name = "latitud", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitud;
    
    @Column(name = "longitud", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitud;
    
    @Column(name = "altitud_msnm", precision = 6, scale = 2)
    private BigDecimal altitudMsnm;
    
    @Column(name = "zona_agroecologica", length = 100)
    private String zonaAgroecologica;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
