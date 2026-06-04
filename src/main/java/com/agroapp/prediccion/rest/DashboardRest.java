package com.agroapp.prediccion.rest;

import com.agroapp.prediccion.finanzas.service.AlertaClimaticaService;
import com.agroapp.prediccion.gestion.service.CosechaService;
import com.agroapp.prediccion.gestion.service.ParcelaService;
import com.agroapp.prediccion.gestion.service.SiembraService;
import com.agroapp.prediccion.mlclima.service.PrediccionRendimientoService;
import com.agroapp.prediccion.seguridad.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardRest {
    
    private final UsuarioService usuarioService;
    private final ParcelaService parcelaService;
    private final SiembraService siembraService;
    private final CosechaService cosechaService;
    private final PrediccionRendimientoService prediccionService;
    private final AlertaClimaticaService alertaService;
    
    @GetMapping("/estadisticas")
    public Map<String, Object> getEstadisticasGenerales() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        estadisticas.put("totalUsuarios", usuarioService.findAll().size());
        estadisticas.put("totalParcelas", parcelaService.findAll().size());
        estadisticas.put("totalSiembras", siembraService.findAll().size());
        estadisticas.put("totalCosechas", cosechaService.findAll().size());
        estadisticas.put("totalPredicciones", prediccionService.findAll().size());
        estadisticas.put("alertasActivas", alertaService.findAll().stream()
                .mapToInt(alerta -> alerta.getActiva() ? 1 : 0).sum());
        
        return estadisticas;
    }
    
    @GetMapping("/usuario/{idUsuario}/resumen")
    public Map<String, Object> getResumenUsuario(@PathVariable Integer idUsuario) {
        Map<String, Object> resumen = new HashMap<>();
        
        var parcelas = parcelaService.findByUsuario(idUsuario);
        var siembras = siembraService.findByUsuario(idUsuario);
        
        resumen.put("totalParcelas", parcelas.size());
        resumen.put("totalSiembras", siembras.size());
        resumen.put("areaTotal", parcelas.stream()
                .mapToDouble(p -> p.getAreaHectareas().doubleValue()).sum());
        
        return resumen;
    }
}