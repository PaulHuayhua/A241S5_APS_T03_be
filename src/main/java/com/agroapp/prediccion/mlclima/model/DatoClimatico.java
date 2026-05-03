package com.agroapp.prediccion.mlclima.model;

import com.agroapp.prediccion.geografia.model.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dato_climatico")
public class DatoClimatico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dato_clima")
    private Integer idDatoClima;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "granularidad", nullable = false, length = 20)
    private String granularidad = "diario";
    
    @Column(name = "temp_max_c", precision = 5, scale = 2)
    private BigDecimal tempMaxC;
    
    @Column(name = "temp_min_c", precision = 5, scale = 2)
    private BigDecimal tempMinC;
    
    @Column(name = "temp_promedio_c", precision = 5, scale = 2)
    private BigDecimal tempPromedioC;
    
    @Column(name = "precipitacion_mm", precision = 6, scale = 2)
    private BigDecimal precipitacionMm;
    
    @Column(name = "humedad_relativa_pct", precision = 4, scale = 2)
    private BigDecimal humedadRelativaPct;
    
    @Column(name = "radiacion_solar_mj_m2", precision = 6, scale = 2)
    private BigDecimal radiacionSolarMjM2;
    
    @Column(name = "eto_mm", precision = 6, scale = 2)
    private BigDecimal etoMm;
    
    @Column(name = "velocidad_viento_ms", precision = 5, scale = 2)
    private BigDecimal velocidadVientoMs;
    
    @Column(name = "fuente", length = 100)
    private String fuente;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
