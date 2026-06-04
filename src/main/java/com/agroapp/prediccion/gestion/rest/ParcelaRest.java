package com.agroapp.prediccion.gestion.rest;

import com.agroapp.prediccion.catalogos.model.TipoSuelo;
import com.agroapp.prediccion.catalogos.service.TipoSueloService;
import com.agroapp.prediccion.geografia.model.Region;
import com.agroapp.prediccion.geografia.service.RegionService;
import com.agroapp.prediccion.gestion.model.Parcela;
import com.agroapp.prediccion.gestion.service.ParcelaService;
import com.agroapp.prediccion.seguridad.model.Usuario;
import com.agroapp.prediccion.seguridad.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parcelas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ParcelaRest {
    
    private final ParcelaService parcelaService;
    private final RegionService regionService;
    private final TipoSueloService tipoSueloService;
    private final UsuarioService usuarioService;
    
    @GetMapping
    public List<Parcela> getAllParcelas() {
        return parcelaService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Parcela> getParcelaById(@PathVariable Integer id) {
        return parcelaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public List<Parcela> getParcelasByUsuario(@PathVariable Integer idUsuario) {
        return parcelaService.findByUsuario(idUsuario);
    }
    
    @PostMapping
    public ResponseEntity<?> createParcela(@RequestBody Parcela parcela) {
        try {
            // Cargar las entidades relacionadas desde la base de datos
            if (parcela.getRegion() != null && parcela.getRegion().getIdRegion() != null) {
                Region region = regionService.findById(parcela.getRegion().getIdRegion())
                    .orElseThrow(() -> new RuntimeException("Región no encontrada"));
                parcela.setRegion(region);
            }
            
            if (parcela.getTipoSuelo() != null && parcela.getTipoSuelo().getIdTipoSuelo() != null) {
                TipoSuelo tipoSuelo = tipoSueloService.findById(parcela.getTipoSuelo().getIdTipoSuelo())
                    .orElseThrow(() -> new RuntimeException("Tipo de suelo no encontrado"));
                parcela.setTipoSuelo(tipoSuelo);
            }
            
            if (parcela.getUsuario() != null && parcela.getUsuario().getIdUsuario() != null) {
                Usuario usuario = usuarioService.findById(parcela.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                parcela.setUsuario(usuario);
            }
            
            Parcela saved = parcelaService.save(parcela);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateParcela(@PathVariable Integer id, @RequestBody Parcela parcela) {
        try {
            return parcelaService.findById(id)
                    .map(existing -> {
                        parcela.setIdParcela(id);
                        parcela.setActualizadoEn(LocalDateTime.now());
                        
                        // Cargar las entidades relacionadas si vienen solo con IDs
                        if (parcela.getRegion() != null && parcela.getRegion().getIdRegion() != null) {
                            Region region = regionService.findById(parcela.getRegion().getIdRegion())
                                .orElse(existing.getRegion());
                            parcela.setRegion(region);
                        } else {
                            parcela.setRegion(existing.getRegion());
                        }
                        
                        if (parcela.getTipoSuelo() != null && parcela.getTipoSuelo().getIdTipoSuelo() != null) {
                            TipoSuelo tipoSuelo = tipoSueloService.findById(parcela.getTipoSuelo().getIdTipoSuelo())
                                .orElse(existing.getTipoSuelo());
                            parcela.setTipoSuelo(tipoSuelo);
                        } else {
                            parcela.setTipoSuelo(existing.getTipoSuelo());
                        }
                        
                        if (parcela.getUsuario() != null && parcela.getUsuario().getIdUsuario() != null) {
                            Usuario usuario = usuarioService.findById(parcela.getUsuario().getIdUsuario())
                                .orElse(existing.getUsuario());
                            parcela.setUsuario(usuario);
                        } else {
                            parcela.setUsuario(existing.getUsuario());
                        }
                        
                        return ResponseEntity.ok(parcelaService.save(parcela));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcela(@PathVariable Integer id) {
        if (parcelaService.findById(id).isPresent()) {
            parcelaService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}