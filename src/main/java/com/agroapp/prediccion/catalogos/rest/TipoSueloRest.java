package com.agroapp.prediccion.catalogos.rest;

import com.agroapp.prediccion.catalogos.service.TipoSueloService;
import com.agroapp.prediccion.catalogos.model.TipoSuelo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-suelo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoSueloRest {
    
    private final TipoSueloService tipoSueloService;
    
    @GetMapping
    public List<TipoSuelo> getAllTiposSuelo() {
        return tipoSueloService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TipoSuelo> getTipoSueloById(@PathVariable Integer id) {
        return tipoSueloService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public TipoSuelo createTipoSuelo(@RequestBody TipoSuelo tipoSuelo) {
        return tipoSueloService.save(tipoSuelo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TipoSuelo> updateTipoSuelo(@PathVariable Integer id, @RequestBody TipoSuelo tipoSuelo) {
        return tipoSueloService.findById(id)
                .map(existing -> {
                    tipoSuelo.setIdTipoSuelo(id);
                    return ResponseEntity.ok(tipoSueloService.save(tipoSuelo));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoSuelo(@PathVariable Integer id) {
        if (tipoSueloService.findById(id).isPresent()) {
            tipoSueloService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}