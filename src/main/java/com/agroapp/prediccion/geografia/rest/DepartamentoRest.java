package com.agroapp.prediccion.geografia.rest;

import com.agroapp.prediccion.geografia.model.Departamento;
import com.agroapp.prediccion.geografia.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DepartamentoRest {
    
    private final DepartamentoService departamentoService;
    
    @GetMapping
    public List<Departamento> getAllDepartamentos() {
        return departamentoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Integer id) {
        return departamentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Departamento createDepartamento(@RequestBody Departamento departamento) {
        return departamentoService.save(departamento);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Departamento> updateDepartamento(@PathVariable Integer id, @RequestBody Departamento departamento) {
        return departamentoService.findById(id)
                .map(existing -> {
                    departamento.setIdDepartamento(id);
                    return ResponseEntity.ok(departamentoService.save(departamento));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Integer id) {
        if (departamentoService.findById(id).isPresent()) {
            departamentoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}