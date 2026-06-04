package com.agroapp.prediccion.gestion.model;

import com.agroapp.prediccion.catalogos.model.TipoSuelo;
import com.agroapp.prediccion.geografia.model.Region;
import com.agroapp.prediccion.seguridad.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "parcela")
public class Parcela {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcela")
    private Integer idParcela;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_suelo", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TipoSuelo tipoSuelo;
    
    @Column(name = "nombre_parcela", nullable = false, length = 100)
    private String nombreParcela;
    
    @Column(name = "codigo_catastral", length = 50)
    private String codigoCatastral;
    
    @Column(name = "area_hectareas", nullable = false, precision = 10, scale = 4)
    private BigDecimal areaHectareas;
    
    @Column(name = "sistema_riego", nullable = false, length = 50)
    private String sistemaRiego;
    
    @Column(name = "ph_suelo", precision = 3, scale = 2)
    private BigDecimal phSuelo;
    
    @Column(name = "materia_organica_pct", precision = 4, scale = 2)
    private BigDecimal materiaOrganicaPct;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
    
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
}
