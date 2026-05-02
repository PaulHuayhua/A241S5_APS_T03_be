package com.agroapp.prediccion.geografia.rest;

import com.agroapp.prediccion.geografia.model.Region;
import com.agroapp.prediccion.geografia.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regiones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegionRest {
    
    private final RegionService regionService;
    
    @GetMapping
    public List<Region> getAllRegiones() {
        return regionService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Integer id) {
        return regionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/departamento/{idDepartamento}")
    public List<Region> getRegionesByDepartamento(@PathVariable Integer idDepartamento) {
        return regionService.findByDepartamento(idDepartamento);
    }
    
    @PostMapping
    public Region createRegion(@RequestBody Region region) {
        return regionService.save(region);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        return regionService.findById(id)
                .map(existing -> {
                    region.setIdRegion(id);
                    return ResponseEntity.ok(regionService.save(region));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        if (regionService.findById(id).isPresent()) {
            regionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}