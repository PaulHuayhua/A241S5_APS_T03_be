package com.agroapp.prediccion.catalogos.service;

import java.util.List;
import java.util.Map;

public interface GroqService {
    
    Map<String, Object> obtenerDatosCultivo(String nombreCultivo);
    
    Map<String, Object> obtenerDatosVariedad(String nombreCultivo, String nombreVariedad);
    
    List<String> sugerirVariedades(String nombreCultivo, String region);
}
