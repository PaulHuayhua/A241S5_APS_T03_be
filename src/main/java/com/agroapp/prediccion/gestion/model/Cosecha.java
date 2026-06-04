package com.agroapp.prediccion.gestion.model;

import com.agroapp.prediccion.seguridad.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cosecha")
public class Cosecha {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cosecha")
    private Integer idCosecha;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_siembra", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Siembra siembra;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "passwordHash"})
    private Usuario usuario;
    
    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;
    
    @Column(name = "area_cosechada_ha", nullable = false, precision = 10, scale = 4)
    private BigDecimal areaCosechadaHa;
    
    @Column(name = "produccion_kg", nullable = false, precision = 12, scale = 2)
    private BigDecimal produccionKg;
    
    @Column(name = "rendimiento_ton_ha", nullable = false, precision = 6, scale = 2)
    private BigDecimal rendimientoTonHa;
    
    @Column(name = "humedad_pct", precision = 4, scale = 2)
    private BigDecimal humedadPct;
    
    @Column(name = "calidad_grado", length = 20)
    private String calidadGrado;
    
    @Column(name = "metodo_medicion", nullable = false, length = 50)
    private String metodoMedicion;
    
    @Column(name = "precio_venta_kg", precision = 8, scale = 2)
    private BigDecimal precioVentaKg;
    
    @Column(name = "canal_venta", length = 100)
    private String canalVenta;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
    
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
}
