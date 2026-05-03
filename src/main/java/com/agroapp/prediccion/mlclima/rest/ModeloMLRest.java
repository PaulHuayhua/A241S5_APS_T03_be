package com.agroapp.prediccion.mlclima.rest;

import com.agroapp.prediccion.mlclima.model.ModeloML;
import com.agroapp.prediccion.mlclima.service.ModeloMLService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos-ml")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ModeloMLRest {
    
    private final ModeloMLService modeloMLService;
    
    @GetMapping
    public List<ModeloML> getAllModelos() {
        return modeloMLService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ModeloML> getModeloById(@PathVariable Integer id) {
        return modeloMLService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ModeloML> getModeloByNombre(@PathVariable String nombre) {
        return modeloMLService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ModeloML createModelo(@RequestBody ModeloML modelo) {
        return modeloMLService.save(modelo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ModeloML> updateModelo(@PathVariable Integer id, @RequestBody ModeloML modelo) {
        return modeloMLService.findById(id)
                .map(existing -> {
                    modelo.setIdModelo(id);
                    return ResponseEntity.ok(modeloMLService.save(modelo));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelo(@PathVariable Integer id) {
        if (modeloMLService.findById(id).isPresent()) {
            modeloMLService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}