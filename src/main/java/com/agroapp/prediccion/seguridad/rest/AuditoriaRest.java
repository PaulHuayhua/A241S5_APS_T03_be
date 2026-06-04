package com.agroapp.prediccion.seguridad.rest;

import com.agroapp.prediccion.seguridad.model.Auditoria;
import com.agroapp.prediccion.seguridad.service.AuditoriaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuditoriaRest {
    
    private final AuditoriaService auditoriaService;
    
    @GetMapping
    public List<Auditoria> getAllAuditorias() {
        return auditoriaService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> getAuditoriaById(@PathVariable Integer id) {
        return auditoriaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Auditoria createAuditoria(@RequestBody Auditoria auditoria) {
        return auditoriaService.save(auditoria);
    }
}