package com.agroapp.prediccion.catalogos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cultivo")
public class Cultivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cultivo")
    private Integer idCultivo;
    
    @Column(name = "nombre_comun", nullable = false, length = 100)
    private String nombreComun;
    
    @Column(name = "nombre_cientifico", length = 150)
    private String nombreCientifico;
    
    @Column(name = "tipo_cultivo", nullable = false, length = 50)
    private String tipoCultivo;
    
    @Column(name = "ciclo_dias_promedio", nullable = false)
    private Integer cicloDiasPromedio;
    
    @Column(name = "temp_optima_min_c", precision = 4, scale = 2)
    private BigDecimal tempOptimaMinC;
    
    @Column(name = "temp_optima_max_c", precision = 4, scale = 2)
    private BigDecimal tempOptimaMaxC;
    
    @Column(name = "precipitacion_min_mm", precision = 6, scale = 2)
    private BigDecimal precipitacionMinMm;
    
    @Column(name = "precipitacion_max_mm", precision = 6, scale = 2)
    private BigDecimal precipitacionMaxMm;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
