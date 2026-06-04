package com.agroapp.prediccion.gestion.rest;

import com.agroapp.prediccion.gestion.service.CosechaService;
import com.agroapp.prediccion.gestion.model.Cosecha;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosechas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CosechaRest {
    
    private final CosechaService cosechaService;
    
    @GetMapping
    public List<Cosecha> getAllCosechas() {
        return cosechaService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cosecha> getCosechaById(@PathVariable Integer id) {
        return cosechaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/siembra/{idSiembra}")
    public List<Cosecha> getCosechasBySiembra(@PathVariable Integer idSiembra) {
        return cosechaService.findBySiembra(idSiembra);
    }
    
    @PostMapping
    public Cosecha createCosecha(@RequestBody Cosecha cosecha) {
        return cosechaService.save(cosecha);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cosecha> updateCosecha(@PathVariable Integer id, @RequestBody Cosecha cosecha) {
        return cosechaService.findById(id)
                .map(existing -> {
                    cosecha.setIdCosecha(id);
                    return ResponseEntity.ok(cosechaService.save(cosecha));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCosecha(@PathVariable Integer id) {
        if (cosechaService.findById(id).isPresent()) {
            cosechaService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}