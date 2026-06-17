package com.agroapp.prediccion.gestion.rest;

import com.agroapp.prediccion.gestion.service.CosechaService;
import com.agroapp.prediccion.gestion.service.SiembraService;
import com.agroapp.prediccion.gestion.model.Cosecha;
import com.agroapp.prediccion.gestion.model.Siembra;
import com.agroapp.prediccion.seguridad.model.Usuario;
import com.agroapp.prediccion.seguridad.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cosechas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CosechaRest {
    
    private final CosechaService cosechaService;
    private final SiembraService siembraService;
    private final UsuarioRepository usuarioRepository;
    
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
    public ResponseEntity<?> createCosecha(@RequestBody Cosecha cosecha) {
        try {
            // Resolver siembra por ID
            if (cosecha.getSiembra() != null && cosecha.getSiembra().getIdSiembra() != null) {
                Siembra siembra = siembraService.findById(cosecha.getSiembra().getIdSiembra())
                        .orElseThrow(() -> new RuntimeException("Siembra no encontrada: " + cosecha.getSiembra().getIdSiembra()));
                cosecha.setSiembra(siembra);
            }
            
            // Resolver usuario por ID
            if (cosecha.getUsuario() != null && cosecha.getUsuario().getIdUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(cosecha.getUsuario().getIdUsuario())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + cosecha.getUsuario().getIdUsuario()));
                cosecha.setUsuario(usuario);
            }
            
            // Asegurar timestamp de creacion
            if (cosecha.getCreadoEn() == null) {
                cosecha.setCreadoEn(LocalDateTime.now());
            }
            
            Cosecha saved = cosechaService.save(cosecha);
            log.info("Cosecha creada con ID: {}", saved.getIdCosecha());
            return ResponseEntity.ok(saved);
            
        } catch (Exception e) {
            log.error("Error al crear cosecha: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCosecha(@PathVariable Integer id, @RequestBody Cosecha cosecha) {
        return cosechaService.findById(id)
                .map(existing -> {
                    try {
                        // Resolver siembra por ID
                        if (cosecha.getSiembra() != null && cosecha.getSiembra().getIdSiembra() != null) {
                            Siembra siembra = siembraService.findById(cosecha.getSiembra().getIdSiembra())
                                    .orElse(existing.getSiembra());
                            existing.setSiembra(siembra);
                        }
                        
                        // Resolver usuario por ID
                        if (cosecha.getUsuario() != null && cosecha.getUsuario().getIdUsuario() != null) {
                            Usuario usuario = usuarioRepository.findById(cosecha.getUsuario().getIdUsuario())
                                    .orElse(existing.getUsuario());
                            existing.setUsuario(usuario);
                        }
                        
                        // Actualizar campos
                        if (cosecha.getFechaCosecha() != null) existing.setFechaCosecha(cosecha.getFechaCosecha());
                        if (cosecha.getAreaCosechadaHa() != null) existing.setAreaCosechadaHa(cosecha.getAreaCosechadaHa());
                        if (cosecha.getProduccionKg() != null) existing.setProduccionKg(cosecha.getProduccionKg());
                        if (cosecha.getRendimientoTonHa() != null) existing.setRendimientoTonHa(cosecha.getRendimientoTonHa());
                        if (cosecha.getMetodoMedicion() != null) existing.setMetodoMedicion(cosecha.getMetodoMedicion());
                        if (cosecha.getCalidadGrado() != null) existing.setCalidadGrado(cosecha.getCalidadGrado());
                        existing.setHumedadPct(cosecha.getHumedadPct());
                        existing.setNotas(cosecha.getNotas());
                        existing.setActualizadoEn(LocalDateTime.now());
                        
                        Cosecha updated = cosechaService.save(existing);
                        log.info("Cosecha actualizada ID: {}", updated.getIdCosecha());
                        return ResponseEntity.ok(updated);
                        
                    } catch (Exception e) {
                        log.error("Error al actualizar cosecha: {}", e.getMessage(), e);
                        return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                    }
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