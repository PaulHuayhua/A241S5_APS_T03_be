package com.agroapp.prediccion.finanzas.model;

import com.agroapp.prediccion.geografia.model.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "alerta_climatica")
public class AlertaClimatica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
    
    @Column(name = "tipo_evento", nullable = false, length = 50)
    private String tipoEvento;
    
    @Column(name = "nivel_riesgo", nullable = false, length = 20)
    private String nivelRiesgo;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "activa", nullable = false)
    private Boolean activa = true;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
