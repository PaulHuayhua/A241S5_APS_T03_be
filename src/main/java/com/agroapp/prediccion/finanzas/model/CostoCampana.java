package com.agroapp.prediccion.finanzas.model;

import com.agroapp.prediccion.gestion.model.Siembra;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "costo_campana")
public class CostoCampana {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_costo")
    private Integer idCosto;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_siembra", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Siembra siembra;
    
    @Column(name = "tipo_costo", nullable = false, length = 100)
    private String tipoCosto;
    
    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;
    
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "fecha_gasto", nullable = false)
    private LocalDate fechaGasto;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "proveedor", length = 200)
    private String proveedor;
    
    @Column(name = "comprobante", length = 100)
    private String comprobante;
    
    @Column(name = "registrado_en", nullable = false, updatable = false)
    private LocalDateTime registradoEn = LocalDateTime.now();
}
