package com.agroapp.prediccion.catalogos.rest;

import com.agroapp.prediccion.catalogos.model.Cultivo;
import com.agroapp.prediccion.catalogos.service.CultivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cultivos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CultivoRest {
    
    private final CultivoService cultivoService;
    
    /**
     * GET /cultivos - Obtiene todos los cultivos activos
     */
    @GetMapping
    public ResponseEntity<List<Cultivo>> getAllCultivos(
            @RequestParam(required = false, defaultValue = "false") boolean includeInactive
    ) {
        if (includeInactive) {
            return ResponseEntity.ok(cultivoService.findAllIncludingInactive());
        }
        return ResponseEntity.ok(cultivoService.findAll());
    }
    
    /**
     * GET /cultivos/{id} - Obtiene un cultivo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCultivoById(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "false") boolean includeInactive
    ) {
        if (includeInactive) {
            return cultivoService.findByIdIncludingInactive(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        
        return cultivoService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "error", "Cultivo no encontrado",
                            "mensaje", "El cultivo con ID " + id + " no existe o está inactivo"
                        )));
    }
    
    /**
     * GET /cultivos/buscar - Busca cultivos por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Cultivo>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(cultivoService.findByNombreComun(nombre));
    }
    
    /**
     * GET /cultivos/tipo/{tipo} - Busca cultivos por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Cultivo>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(cultivoService.findByTipo(tipo));
    }
    
    /**
     * POST /cultivos - Crea un nuevo cultivo
     */
    @PostMapping
    public ResponseEntity<?> createCultivo(@RequestBody Cultivo cultivo) {
        try {
            // Validaciones básicas
            if (cultivo.getNombreComun() == null || cultivo.getNombreComun().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "error", "Datos inválidos",
                            "mensaje", "El nombre común del cultivo es obligatorio"
                        ));
            }
            
            if (cultivo.getTipoCultivo() == null || cultivo.getTipoCultivo().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "error", "Datos inválidos",
                            "mensaje", "El tipo de cultivo es obligatorio"
                        ));
            }
            
            if (cultivo.getCicloDiasPromedio() == null || cultivo.getCicloDiasPromedio() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "error", "Datos inválidos",
                            "mensaje", "El ciclo en días debe ser mayor a 0"
                        ));
            }
            
            Cultivo nuevoCultivo = cultivoService.save(cultivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCultivo);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "error", "Error al crear el cultivo",
                        "mensaje", e.getMessage()
                    ));
        }
    }
    
    /**
     * PUT /cultivos/{id} - Actualiza un cultivo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCultivo(
            @PathVariable Integer id,
            @RequestBody Cultivo cultivo
    ) {
        try {
            return cultivoService.findByIdIncludingInactive(id)
                    .map(existing -> {
                        // Validaciones básicas
                        if (cultivo.getNombreComun() == null || cultivo.getNombreComun().trim().isEmpty()) {
                            return ResponseEntity.badRequest()
                                    .body(Map.of(
                                        "error", "Datos inválidos",
                                        "mensaje", "El nombre común del cultivo es obligatorio"
                                    ));
                        }
                        
                        cultivo.setIdCultivo(id);
                        // Mantener el estado activo/inactivo del cultivo existente
                        cultivo.setActivo(existing.getActivo());
                        
                        Cultivo cultivoActualizado = cultivoService.save(cultivo);
                        return ResponseEntity.ok(cultivoActualizado);
                    })
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of(
                                "error", "Cultivo no encontrado",
                                "mensaje", "El cultivo con ID " + id + " no existe"
                            )));
                            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "error", "Error al actualizar el cultivo",
                        "mensaje", e.getMessage()
                    ));
        }
    }
    
    /**
     * DELETE /cultivos/{id} - Eliminación lógica de un cultivo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCultivo(@PathVariable Integer id) {
        try {
            if (cultivoService.findByIdIncludingInactive(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "error", "Cultivo no encontrado",
                            "mensaje", "El cultivo con ID " + id + " no existe"
                        ));
            }
            
            boolean eliminado = cultivoService.deleteLogically(id);
            
            if (eliminado) {
                return ResponseEntity.ok()
                        .body(Map.of(
                            "mensaje", "Cultivo eliminado correctamente",
                            "nota", "El cultivo ha sido marcado como inactivo y puede ser reactivado"
                        ));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "error", "No se pudo eliminar",
                            "mensaje", "El cultivo ya está inactivo"
                        ));
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "error", "Error al eliminar el cultivo",
                        "mensaje", e.getMessage()
                    ));
        }
    }
    
    /**
     * POST /cultivos/{id}/reactivar - Reactiva un cultivo eliminado
     */
    @PostMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivarCultivo(@PathVariable Integer id) {
        try {
            if (cultivoService.findByIdIncludingInactive(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "error", "Cultivo no encontrado",
                            "mensaje", "El cultivo con ID " + id + " no existe"
                        ));
            }
            
            boolean reactivado = cultivoService.reactivate(id);
            
            if (reactivado) {
                return ResponseEntity.ok()
                        .body(Map.of(
                            "mensaje", "Cultivo reactivado correctamente"
                        ));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                            "error", "No se pudo reactivar",
                            "mensaje", "El cultivo ya está activo"
                        ));
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "error", "Error al reactivar el cultivo",
                        "mensaje", e.getMessage()
                    ));
        }
    }
    
    /**
     * GET /cultivos/{id}/relaciones - Verifica si el cultivo tiene relaciones
     */
    @GetMapping("/{id}/relaciones")
    public ResponseEntity<?> verificarRelaciones(@PathVariable Integer id) {
        if (cultivoService.findByIdIncludingInactive(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        boolean tieneRelaciones = cultivoService.tieneRelaciones(id);
        
        return ResponseEntity.ok()
                .body(Map.of(
                    "tieneRelaciones", tieneRelaciones,
                    "mensaje", tieneRelaciones 
                        ? "El cultivo tiene variedades asociadas" 
                        : "El cultivo no tiene relaciones"
                ));
    }
}
