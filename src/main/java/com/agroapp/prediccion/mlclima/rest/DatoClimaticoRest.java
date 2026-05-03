package com.agroapp.prediccion.mlclima.rest;

import com.agroapp.prediccion.mlclima.model.DatoClimatico;
import com.agroapp.prediccion.mlclima.service.DatoClimaticoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/datos-climaticos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DatoClimaticoRest {
    
    private final DatoClimaticoService datoClimaticoService;
    
    @GetMapping
    public List<DatoClimatico> getAllDatosClimaticos() {
        return datoClimaticoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatoClimatico> getDatoClimaticoById(@PathVariable Integer id) {
        return datoClimaticoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/region/{idRegion}")
    public List<DatoClimatico> getDatosClimaticosByRegionAndFechas(
            @PathVariable Integer idRegion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return datoClimaticoService.findByRegionAndFechas(idRegion, fechaInicio, fechaFin);
    }
    
    @PostMapping
    public DatoClimatico createDatoClimatico(@RequestBody DatoClimatico datoClimatico) {
        return datoClimaticoService.save(datoClimatico);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DatoClimatico> updateDatoClimatico(@PathVariable Integer id, @RequestBody DatoClimatico datoClimatico) {
        return datoClimaticoService.findById(id)
                .map(existing -> {
                    datoClimatico.setIdDatoClima(id);
                    return ResponseEntity.ok(datoClimaticoService.save(datoClimatico));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatoClimatico(@PathVariable Integer id) {
        if (datoClimaticoService.findById(id).isPresent()) {
            datoClimaticoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}