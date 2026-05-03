package com.agroapp.prediccion.mlclima.model;

import com.agroapp.prediccion.gestion.model.Siembra;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prediccion_rendimiento")
public class PrediccionRendimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prediccion")
    private Integer idPrediccion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_siembra", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Siembra siembra;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modelo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ModeloML modelo;
    
    @Column(name = "fecha_prediccion", nullable = false)
    private LocalDate fechaPrediccion;
    
    @Column(name = "fecha_clima_inicio", nullable = false)
    private LocalDate fechaClimaInicio;
    
    @Column(name = "fecha_clima_fin", nullable = false)
    private LocalDate fechaClimaFin;
    
    @Column(name = "fuente_clima", length = 100)
    private String fuenteClima;
    
    @Column(name = "rendimiento_estimado_ton_ha", nullable = false, precision = 6, scale = 2)
    private BigDecimal rendimientoEstimadoTonHa;
    
    @Column(name = "rendimiento_min_ton_ha", nullable = false, precision = 6, scale = 2)
    private BigDecimal rendimientoMinTonHa;
    
    @Column(name = "rendimiento_max_ton_ha", nullable = false, precision = 6, scale = 2)
    private BigDecimal rendimientoMaxTonHa;
    
    @Column(name = "intervalo_confianza_pct", nullable = false, precision = 4, scale = 2)
    private BigDecimal intervaloConfianzaPct;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "activa";
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
    
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
}
