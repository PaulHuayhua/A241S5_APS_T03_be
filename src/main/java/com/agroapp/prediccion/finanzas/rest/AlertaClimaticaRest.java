package com.agroapp.prediccion.finanzas.rest;

import com.agroapp.prediccion.finanzas.model.AlertaClimatica;
import com.agroapp.prediccion.finanzas.service.AlertaClimaticaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas-climaticas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlertaClimaticaRest {
    
    private final AlertaClimaticaService alertaService;
    
    @GetMapping
    public List<AlertaClimatica> getAllAlertas() {
        return alertaService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlertaClimatica> getAlertaById(@PathVariable Integer id) {
        return alertaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/region/{idRegion}/activas")
    public List<AlertaClimatica> getAlertasActivasByRegion(@PathVariable Integer idRegion) {
        return alertaService.findByRegionActivas(idRegion);
    }
    
    @PostMapping
    public AlertaClimatica createAlerta(@RequestBody AlertaClimatica alerta) {
        return alertaService.save(alerta);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AlertaClimatica> updateAlerta(@PathVariable Integer id, @RequestBody AlertaClimatica alerta) {
        return alertaService.findById(id)
                .map(existing -> {
                    alerta.setIdAlerta(id);
                    return ResponseEntity.ok(alertaService.save(alerta));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Integer id) {
        if (alertaService.findById(id).isPresent()) {
            alertaService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}