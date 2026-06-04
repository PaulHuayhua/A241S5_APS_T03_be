package com.agroapp.prediccion.seguridad.rest;

import com.agroapp.prediccion.seguridad.model.Rol;
import com.agroapp.prediccion.seguridad.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolRest {
    
    private final RolService rolService;
    
    @GetMapping
    public List<Rol> getAllRoles() {
        return rolService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Integer id) {
        return rolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Rol> getRolByNombre(@PathVariable String nombre) {
        return rolService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Rol createRol(@RequestBody Rol rol) {
        return rolService.save(rol);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Integer id, @RequestBody Rol rol) {
        return rolService.findById(id)
                .map(existing -> {
                    rol.setIdRol(id);
                    return ResponseEntity.ok(rolService.save(rol));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        if (rolService.findById(id).isPresent()) {
            rolService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}