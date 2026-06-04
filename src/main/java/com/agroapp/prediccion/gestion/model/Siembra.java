package com.agroapp.prediccion.gestion.model;

import com.agroapp.prediccion.catalogos.model.Variedad;
import com.agroapp.prediccion.seguridad.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "siembra")
public class Siembra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_siembra")
    private Integer idSiembra;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_parcela", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Parcela parcela;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_variedad", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Variedad variedad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private Usuario usuario;
    
    @Column(name = "codigo_campana", length = 50)
    private String codigoCampana;
    
    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;
    
    @Column(name = "area_sembrada_ha", nullable = false, precision = 10, scale = 4)
    private BigDecimal areaSembradaHa;
    
    @Column(name = "densidad_plantas_ha")
    private Integer densidadPlantasHa;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "en_curso";
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
    
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
}
