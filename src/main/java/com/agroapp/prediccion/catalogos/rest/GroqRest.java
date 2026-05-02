package com.agroapp.prediccion.catalogos.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agroapp.prediccion.catalogos.service.GroqService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/groq")
@RequiredArgsConstructor
@CrossOrigin(
    origins = {"http://localhost:3000", "http://localhost:5173"},
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
    allowedHeaders = "*",
    allowCredentials = "true"
)
public class GroqRest {

    private final GroqService groqService;

    /**
     * Autocompleta datos de un cultivo usando IA
     * GET /groq/cultivo/autocompletar?nombre=Maíz
     */
    @GetMapping("/cultivo/autocompletar")
    public ResponseEntity<Map<String, Object>> autocompletarCultivo(
            @RequestParam String nombre
    ) {
        try {
            Map<String, Object> datos = groqService.obtenerDatosCultivo(nombre);
            
            if (datos.containsKey("error")) {
                return ResponseEntity.badRequest().body(datos);
            }
            
            return ResponseEntity.ok(datos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Error al procesar la solicitud",
                    "mensaje", e.getMessage() != null ? e.getMessage() : "Error desconocido"
                )
            );
        }
    }

    /**
     * Autocompleta datos de una variedad usando IA
     * GET /groq/variedad/autocompletar?cultivo=Maíz&variedad=Amarillo Duro
     */
    @GetMapping("/variedad/autocompletar")
    public ResponseEntity<Map<String, Object>> autocompletarVariedad(
            @RequestParam String cultivo,
            @RequestParam String variedad
    ) {
        Map<String, Object> datos = groqService.obtenerDatosVariedad(cultivo, variedad);
        
        if (datos.containsKey("error")) {
            return ResponseEntity.badRequest().body(datos);
        }
        
        return ResponseEntity.ok(datos);
    }

    /**
     * Sugiere variedades para un cultivo
     * GET /groq/variedades/sugerir?cultivo=Maíz&region=Lima
     */
    @GetMapping("/variedades/sugerir")
    public ResponseEntity<List<String>> sugerirVariedades(
            @RequestParam String cultivo,
            @RequestParam(required = false, defaultValue = "Costa") String region
    ) {
        List<String> variedades = groqService.sugerirVariedades(cultivo, region);
        return ResponseEntity.ok(variedades);
    }
}
