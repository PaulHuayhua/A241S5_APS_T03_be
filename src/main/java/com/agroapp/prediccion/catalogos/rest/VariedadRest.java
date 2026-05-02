package com.agroapp.prediccion.catalogos.rest;

import com.agroapp.prediccion.catalogos.model.Variedad;
import com.agroapp.prediccion.catalogos.service.VariedadService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variedades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VariedadRest {
    
    private final VariedadService variedadService;
    
    @GetMapping
    public List<Variedad> getAllVariedades() {
        return variedadService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Variedad> getVariedadById(@PathVariable Integer id) {
        return variedadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cultivo/{idCultivo}")
    public List<Variedad> getVariedadesByCultivo(@PathVariable Integer idCultivo) {
        return variedadService.findByCultivo(idCultivo);
    }
    
    @PostMapping
    public Variedad createVariedad(@RequestBody Variedad variedad) {
        return variedadService.save(variedad);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Variedad> updateVariedad(@PathVariable Integer id, @RequestBody Variedad variedad) {
        return variedadService.findById(id)
                .map(existing -> {
                    variedad.setIdVariedad(id);
                    return ResponseEntity.ok(variedadService.save(variedad));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariedad(@PathVariable Integer id) {
        if (variedadService.findById(id).isPresent()) {
            variedadService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}