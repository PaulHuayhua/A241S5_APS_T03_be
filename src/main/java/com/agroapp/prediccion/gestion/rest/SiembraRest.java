package com.agroapp.prediccion.gestion.rest;

import com.agroapp.prediccion.gestion.model.Siembra;
import com.agroapp.prediccion.gestion.service.SiembraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/siembras")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SiembraRest {
    
    private final SiembraService siembraService;
    
    @GetMapping
    public List<Siembra> getAllSiembras() {
        return siembraService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Siembra> getSiembraById(@PathVariable Integer id) {
        return siembraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/parcela/{idParcela}")
    public List<Siembra> getSiembrasByParcela(@PathVariable Integer idParcela) {
        return siembraService.findByParcela(idParcela);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public List<Siembra> getSiembrasByUsuario(@PathVariable Integer idUsuario) {
        return siembraService.findByUsuario(idUsuario);
    }
    
    @PostMapping
    public Siembra createSiembra(@RequestBody Siembra siembra) {
        return siembraService.save(siembra);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Siembra> updateSiembra(@PathVariable Integer id, @RequestBody Siembra siembra) {
        log.info("Actualizando siembra ID {}, estado recibido: {}", id, siembra.getEstado());
        
        return siembraService.findById(id)
                .map(existing -> {
                    log.info("Estado actual en BD: {}", existing.getEstado());
                    
                    // Actualizar solo los campos que vienen en el request
                    if (siembra.getParcela() != null) {
                        existing.setParcela(siembra.getParcela());
                    }
                    if (siembra.getVariedad() != null) {
                        existing.setVariedad(siembra.getVariedad());
                    }
                    if (siembra.getUsuario() != null) {
                        existing.setUsuario(siembra.getUsuario());
                    }
                    if (siembra.getCodigoCampana() != null) {
                        existing.setCodigoCampana(siembra.getCodigoCampana());
                    }
                    if (siembra.getFechaSiembra() != null) {
                        existing.setFechaSiembra(siembra.getFechaSiembra());
                    }
                    if (siembra.getAreaSembradaHa() != null) {
                        existing.setAreaSembradaHa(siembra.getAreaSembradaHa());
                    }
                    if (siembra.getDensidadPlantasHa() != null) {
                        existing.setDensidadPlantasHa(siembra.getDensidadPlantasHa());
                    }
                    if (siembra.getEstado() != null) {
                        log.info("Actualizando estado de '{}' a '{}'", existing.getEstado(), siembra.getEstado());
                        existing.setEstado(siembra.getEstado());
                    }
                    if (siembra.getNotas() != null) {
                        existing.setNotas(siembra.getNotas());
                    }
                    
                    existing.setActualizadoEn(java.time.LocalDateTime.now());
                    Siembra updated = siembraService.save(existing);
                    log.info("Siembra actualizada, nuevo estado: {}", updated.getEstado());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Siembra> updateEstado(@PathVariable Integer id, @RequestBody java.util.Map<String, String> payload) {
        String nuevoEstado = payload.get("estado");
        log.info("Actualizando estado de siembra ID {} a: {}", id, nuevoEstado);
        
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        return siembraService.findById(id)
                .map(existing -> {
                    log.info("Estado actual: {}", existing.getEstado());
                    existing.setEstado(nuevoEstado);
                    existing.setActualizadoEn(java.time.LocalDateTime.now());
                    Siembra updated = siembraService.save(existing);
                    log.info("Estado actualizado a: {}", updated.getEstado());
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiembra(@PathVariable Integer id) {
        if (siembraService.findById(id).isPresent()) {
            siembraService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}