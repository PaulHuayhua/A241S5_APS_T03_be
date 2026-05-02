package com.agroapp.prediccion.catalogos.service;

import java.util.List;
import java.util.Map;

/**
 * Interfaz de servicio para la integración con Groq AI
 */
public interface GroqService {
    
    /**
     * Obtiene información técnica de un cultivo usando Groq
     * @param nombreCultivo Nombre del cultivo a consultar
     * @return Mapa con los datos del cultivo o error
     */
    Map<String, Object> obtenerDatosCultivo(String nombreCultivo);
    
    /**
     * Obtiene información de una variedad específica
     * @param nombreCultivo Nombre del cultivo
     * @param nombreVariedad Nombre de la variedad
     * @return Mapa con los datos de la variedad o error
     */
    Map<String, Object> obtenerDatosVariedad(String nombreCultivo, String nombreVariedad);
    
    /**
     * Sugiere variedades para un cultivo
     * @param nombreCultivo Nombre del cultivo
     * @param region Región geográfica
     * @return Lista de nombres de variedades sugeridas
     */
    List<String> sugerirVariedades(String nombreCultivo, String region);
}
