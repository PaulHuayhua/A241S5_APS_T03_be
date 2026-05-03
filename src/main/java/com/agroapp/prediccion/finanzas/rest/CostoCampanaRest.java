package com.agroapp.prediccion.finanzas.rest;

import com.agroapp.prediccion.finanzas.service.CostoCampanaService;
import com.agroapp.prediccion.finanzas.model.CostoCampana;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/costos-campana")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CostoCampanaRest {
    
    private final CostoCampanaService costoService;
    
    @GetMapping
    public List<CostoCampana> getAllCostos() {
        return costoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CostoCampana> getCostoById(@PathVariable Integer id) {
        return costoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/siembra/{idSiembra}")
    public List<CostoCampana> getCostosBySiembra(@PathVariable Integer idSiembra) {
        return costoService.findBySiembra(idSiembra);
    }
    
    @PostMapping
    public CostoCampana createCosto(@RequestBody CostoCampana costo) {
        return costoService.save(costo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CostoCampana> updateCosto(@PathVariable Integer id, @RequestBody CostoCampana costo) {
        return costoService.findById(id)
                .map(existing -> {
                    costo.setIdCosto(id);
                    return ResponseEntity.ok(costoService.save(costo));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCosto(@PathVariable Integer id) {
        if (costoService.findById(id).isPresent()) {
            costoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}