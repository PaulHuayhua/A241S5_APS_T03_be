package com.agroapp.prediccion.mlclima.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "modelo_ml")
public class ModeloML {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Integer idModelo;
    
    @Column(name = "nombre_modelo", nullable = false, unique = true, length = 100)
    private String nombreModelo;
    
    @Column(name = "algoritmo", nullable = false, length = 100)
    private String algoritmo;
    
    @Column(name = "version", nullable = false, length = 20)
    private String version;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "entrenamiento";
    
    @Column(name = "r2_score", precision = 5, scale = 4)
    private BigDecimal r2Score;
    
    @Column(name = "mae", precision = 8, scale = 4)
    private BigDecimal mae;
    
    @Column(name = "rmse", precision = 8, scale = 4)
    private BigDecimal rmse;
    
    @Column(name = "fecha_entrenamiento", nullable = false)
    private LocalDate fechaEntrenamiento;
    
    @Column(name = "total_registros_entrenamiento", nullable = false)
    private Integer totalRegistrosEntrenamiento;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "features_lista", nullable = false, columnDefinition = "jsonb")
    private List<String> featuresLista;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hiperparametros", columnDefinition = "jsonb")
    private Map<String, Object> hiperparametros;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
