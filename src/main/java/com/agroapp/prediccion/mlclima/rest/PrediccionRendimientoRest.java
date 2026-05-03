package com.agroapp.prediccion.mlclima.rest;

import com.agroapp.prediccion.mlclima.model.PrediccionRendimiento;
import com.agroapp.prediccion.gestion.model.Siembra;
import com.agroapp.prediccion.mlclima.model.ModeloML;
import com.agroapp.prediccion.mlclima.service.ModeloMLService;
import com.agroapp.prediccion.mlclima.service.PrediccionRendimientoService;
import com.agroapp.prediccion.gestion.service.SiembraService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/predicciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PrediccionRendimientoRest {
    
    private final PrediccionRendimientoService prediccionService;
    private final SiembraService siembraService;
    private final ModeloMLService modeloMLService;
    
    @GetMapping
    public List<PrediccionRendimiento> getAllPredicciones() {
        return prediccionService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PrediccionRendimiento> getPrediccionById(@PathVariable Integer id) {
        return prediccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/siembra/{idSiembra}")
    public List<PrediccionRendimiento> getPrediccionesBySiembra(@PathVariable Integer idSiembra) {
        return prediccionService.findBySiembra(idSiembra);
    }
    
    @PostMapping
    public ResponseEntity<?> createPrediccion(@RequestBody PrediccionRendimiento prediccion) {
        try {
            // Cargar las entidades relacionadas desde la base de datos
            if (prediccion.getSiembra() != null && prediccion.getSiembra().getIdSiembra() != null) {
                Siembra siembra = siembraService.findById(prediccion.getSiembra().getIdSiembra())
                    .orElseThrow(() -> new RuntimeException("Siembra no encontrada"));
                prediccion.setSiembra(siembra);
            }
            
            if (prediccion.getModelo() != null && prediccion.getModelo().getIdModelo() != null) {
                ModeloML modelo = modeloMLService.findById(prediccion.getModelo().getIdModelo())
                    .orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
                prediccion.setModelo(modelo);
            }
            
            PrediccionRendimiento saved = prediccionService.save(prediccion);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrediccion(@PathVariable Integer id, @RequestBody PrediccionRendimiento prediccion) {
        try {
            return prediccionService.findById(id)
                    .map(existing -> {
                        prediccion.setIdPrediccion(id);
                        prediccion.setActualizadoEn(LocalDateTime.now());
                        
                        // Cargar las entidades relacionadas si vienen solo con IDs
                        if (prediccion.getSiembra() != null && prediccion.getSiembra().getIdSiembra() != null) {
                            Siembra siembra = siembraService.findById(prediccion.getSiembra().getIdSiembra())
                                .orElse(existing.getSiembra());
                            prediccion.setSiembra(siembra);
                        } else {
                            prediccion.setSiembra(existing.getSiembra());
                        }
                        
                        if (prediccion.getModelo() != null && prediccion.getModelo().getIdModelo() != null) {
                            ModeloML modelo = modeloMLService.findById(prediccion.getModelo().getIdModelo())
                                .orElse(existing.getModelo());
                            prediccion.setModelo(modelo);
                        } else {
                            prediccion.setModelo(existing.getModelo());
                        }
                        
                        return ResponseEntity.ok(prediccionService.save(prediccion));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrediccion(@PathVariable Integer id) {
        if (prediccionService.findById(id).isPresent()) {
            prediccionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}