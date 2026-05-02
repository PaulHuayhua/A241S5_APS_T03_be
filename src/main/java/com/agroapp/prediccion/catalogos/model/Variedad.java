package com.agroapp.prediccion.catalogos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "variedad")
public class Variedad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variedad")
    private Integer idVariedad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cultivo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cultivo cultivo;
    
    @Column(name = "nombre_variedad", nullable = false, length = 100)
    private String nombreVariedad;
    
    @Column(name = "codigo", unique = true, length = 50)
    private String codigo;
    
    @Column(name = "ciclo_dias", nullable = false)
    private Integer cicloDias;
    
    @Column(name = "rendimiento_referencia_ton_ha", precision = 6, scale = 2)
    private BigDecimal rendimientoReferenciaTonHa;
    
    @Column(name = "resistencia_sequia")
    private Boolean resistenciaSequia = false;
    
    @Column(name = "resistencia_helada")
    private Boolean resistenciaHelada = false;
    
    @Column(name = "caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
