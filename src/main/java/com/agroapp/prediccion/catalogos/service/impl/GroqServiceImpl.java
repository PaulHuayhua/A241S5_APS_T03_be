package com.agroapp.prediccion.catalogos.service.impl;

import com.agroapp.prediccion.catalogos.service.GroqService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GroqServiceImpl implements GroqService {

    @Value("${groq.api.key:}")
    private String groqApiKey;

    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> obtenerDatosCultivo(String nombreCultivo) {
        try {
            log.info("Solicitando información del cultivo: {}", nombreCultivo);
            
            String prompt = construirPromptCultivo(nombreCultivo);
            String response = llamarGroqAPI(prompt);
            log.info("Respuesta recibida de Groq para cultivo: {}", nombreCultivo);
            return parsearRespuestaJSON(response);

        } catch (Exception e) {
            log.error("Error al obtener datos del cultivo desde Groq: {}", e.getMessage(), e);
            return Map.of(
                "error", "No se pudo obtener información del cultivo",
                "detalle", e.getMessage() != null ? e.getMessage() : "Error desconocido"
            );
        }
    }

    @Override
    public Map<String, Object> obtenerDatosVariedad(String nombreCultivo, String nombreVariedad) {
        try {
            log.info("Solicitando información de la variedad: {} del cultivo: {}", nombreVariedad, nombreCultivo);
            
            String prompt = construirPromptVariedad(nombreCultivo, nombreVariedad);
            String response = llamarGroqAPI(prompt);
            log.info("Respuesta recibida de Groq para variedad: {} del cultivo: {}", nombreVariedad, nombreCultivo);
            return parsearRespuestaJSON(response);

        } catch (Exception e) {
            log.error("Error al obtener datos de la variedad desde Groq: {}", e.getMessage(), e);
            return Map.of(
                "error", "No se pudo obtener información de la variedad",
                "detalle", e.getMessage() != null ? e.getMessage() : "Error desconocido"
            );
        }
    }

    @Override
    public List<String> sugerirVariedades(String nombreCultivo, String region) {
        try {
            log.info("Solicitando sugerencias de variedades para cultivo: {} en región: {}", nombreCultivo, region);
            
            String prompt = construirPromptSugerencias(nombreCultivo, region);
            String response = llamarGroqAPI(prompt);
            log.info("Respuesta recibida de Groq para sugerencias de variedades");
            List<String> variedades = parsearRespuestaArray(response);
            
            // Limitar a máximo 5 variedades
            if (variedades.size() > 5) {
                return variedades.subList(0, 5);
            }
            
            return variedades;

        } catch (Exception e) {
            log.error("Error al sugerir variedades desde Groq: {}", e.getMessage(), e);
            return List.of();
        }
    }

    /**
     * Construye el prompt para obtener datos de un cultivo
     */
    private String construirPromptCultivo(String nombreCultivo) {
        return String.format(
            "Eres un agrónomo experto con acceso a bases de datos científicas de la FAO, INIA y literatura agronómica verificada.\\n\\n" +
            
            "DATOS DE REFERENCIA CIENTÍFICOS (úsalos como guía para validar tu respuesta):\\n" +
            "- Maíz: temp 15-30°C, precip 500-800mm, ciclo 80-150 días\\n" +
            "- Papa: temp 10-20°C, precip 500-700mm, ciclo 90-150 días\\n" +
            "- Arroz: temp 20-35°C, precip 1200-2000mm, ciclo 120-150 días\\n" +
            "- Quinua: temp 10-20°C, precip 300-600mm, ciclo 150-180 días\\n" +
            "- Trigo: temp 15-25°C, precip 450-650mm, ciclo 120-150 días\\n" +
            "- Frijol: temp 15-27°C, precip 400-600mm, ciclo 80-110 días\\n" +
            "- Tomate: temp 18-27°C, precip 600-800mm, ciclo 90-120 días\\n" +
            "- Cebolla: temp 13-24°C, precip 350-550mm, ciclo 120-180 días\\n" +
            "- Remolacha: temp 15-25°C, precip 600-800mm, ciclo 90-120 días\\n" +
            "- Zanahoria: temp 16-21°C, precip 450-600mm, ciclo 90-120 días\\n" +
            "- Lechuga: temp 15-20°C, precip 380-500mm, ciclo 60-90 días\\n" +
            "- Camote: temp 20-30°C, precip 750-1000mm, ciclo 120-150 días\\n" +
            "- Yuca: temp 25-29°C, precip 1000-1500mm, ciclo 240-360 días\\n" +
            "- Plátano: temp 26-30°C, precip 1500-2500mm, ciclo 270-365 días\\n" +
            "- Café: temp 18-24°C, precip 1500-2000mm, ciclo 270-300 días\\n" +
            "- Cacao: temp 24-28°C, precip 1500-2500mm, ciclo 150-180 días\\n" +
            "- Arándano: temp 12-25°C, precip 800-1200mm, ciclo 180-240 días\\n" +
            "- Palto: temp 20-28°C, precip 1000-1500mm, ciclo 240-365 días\\n\\n" +
            
            "INSTRUCCIONES CRÍTICAS:\\n" +
            "1. Si el cultivo '%s' está en la lista de referencia, USA EXACTAMENTE esos valores\\n" +
            "2. Si no está en la lista pero lo conoces con certeza, proporciona datos verificables similares a los de referencia\\n" +
            "3. Si NO conoces el cultivo con certeza, responde: {\\\"error\\\": \\\"Cultivo no encontrado o no verificable\\\"}\\n" +
            "4. NO inventes datos. La precisión es CRÍTICA para decisiones agrícolas\\n" +
            "5. Los rangos deben ser realistas y basados en ciencia agronómica\\n" +
            "6. Verifica que el nombre científico sea correcto (género y especie)\\n\\n" +
            
            "Responde SOLO con un objeto JSON válido:\\n" +
            "{\\n" +
            "  \\\"nombreComun\\\": \\\"nombre común en español (capitalizado)\\\",\\n" +
            "  \\\"nombreCientifico\\\": \\\"Género especie (formato científico correcto)\\\",\\n" +
            "  \\\"tipoCultivo\\\": \\\"Cereal|Tubérculo|Leguminosa|Hortaliza|Fruta|Perenne|Oleaginosa\\\",\\n" +
            "  \\\"cicloDiasPromedio\\\": número entero (promedio del rango),\\n" +
            "  \\\"tempOptimaMinC\\\": número decimal (límite inferior del rango óptimo),\\n" +
            "  \\\"tempOptimaMaxC\\\": número decimal (límite superior del rango óptimo),\\n" +
            "  \\\"precipitacionMinMm\\\": número entero (límite inferior anual),\\n" +
            "  \\\"precipitacionMaxMm\\\": número entero (límite superior anual)\\n" +
            "}\\n\\n" +
            
            "CULTIVO SOLICITADO: '%s'\\n" +
            "Responde SOLO con el JSON, sin explicaciones adicionales.",
            nombreCultivo, nombreCultivo
        );
    }

    /**
     * Construye el prompt para obtener datos de una variedad
     */
    private String construirPromptVariedad(String nombreCultivo, String nombreVariedad) {
        return String.format(
            "Eres un agrónomo experto especializado en variedades comerciales de cultivos en Perú y Latinoamérica.\\n\\n" +
            
            "VARIEDADES REALES DE REFERENCIA (Perú - INIA y comerciales):\\n\\n" +
            
            "MAÍZ:\\n" +
            "- INIA 619 Megahíbrido: ciclo 150 días, rendimiento 12 ton/ha, resistente sequía\\n" +
            "- Marginal 28T: ciclo 150 días, rendimiento 8-10 ton/ha, costa\\n" +
            "- PM-212: ciclo 140 días, rendimiento 9 ton/ha\\n" +
            "- Dekalb 7088: ciclo 135 días, rendimiento 10 ton/ha\\n\\n" +
            
            "PAPA:\\n" +
            "- Canchán: ciclo 120 días, rendimiento 25-30 ton/ha, resistente virus\\n" +
            "- Yungay: ciclo 150 días, rendimiento 20-25 ton/ha, sierra\\n" +
            "- Perricholi: ciclo 120 días, rendimiento 30-35 ton/ha\\n" +
            "- Única: ciclo 105 días, rendimiento 25 ton/ha, temprana\\n" +
            "- Amarilis: ciclo 120 días, rendimiento 28 ton/ha\\n\\n" +
            
            "ARROZ:\\n" +
            "- IR-43: ciclo 120 días, rendimiento 7-8 ton/ha\\n" +
            "- Tinajones: ciclo 135 días, rendimiento 8 ton/ha\\n" +
            "- La Puntilla: ciclo 130 días, rendimiento 7.5 ton/ha\\n" +
            "- INIA 509: ciclo 125 días, rendimiento 8.5 ton/ha\\n\\n" +
            
            "QUINUA:\\n" +
            "- Salcedo INIA: ciclo 150 días, rendimiento 2.5 ton/ha, altiplano\\n" +
            "- Pasankalla: ciclo 160 días, rendimiento 2 ton/ha, resistente helada\\n" +
            "- Blanca de Juli: ciclo 155 días, rendimiento 2.3 ton/ha\\n\\n" +
            
            "TRIGO:\\n" +
            "- Centenario: ciclo 150 días, rendimiento 4-5 ton/ha\\n" +
            "- Andino: ciclo 140 días, rendimiento 3.5 ton/ha\\n\\n" +
            
            "INSTRUCCIONES CRÍTICAS:\\n" +
            "1. Si la variedad '%s' del cultivo '%s' está en la lista, USA EXACTAMENTE esos valores\\n" +
            "2. Si no está pero conoces la variedad real con certeza, proporciona datos verificables\\n" +
            "3. Si NO conoces la variedad, responde: {\\\"error\\\": \\\"Variedad no encontrada o no verificable\\\"}\\n" +
            "4. NO inventes variedades ni datos. Esto afecta decisiones agrícolas reales\\n" +
            "5. El rendimiento debe ser realista para Perú/Latinoamérica\\n" +
            "6. Las resistencias deben ser características documentadas\\n\\n" +
            
            "Responde SOLO con un objeto JSON válido:\\n" +
            "{\\n" +
            "  \\\"nombreVariedad\\\": \\\"nombre oficial de la variedad\\\",\\n" +
            "  \\\"cicloDias\\\": número entero (días desde siembra hasta cosecha),\\n" +
            "  \\\"rendimientoReferenciaTonHa\\\": número decimal (rendimiento promedio real en ton/ha),\\n" +
            "  \\\"resistenciaSequia\\\": true o false (solo si está documentado),\\n" +
            "  \\\"resistenciaHelada\\\": true o false (solo si está documentado),\\n" +
            "  \\\"caracteristicas\\\": \\\"descripción breve de características agronómicas verificables (máx 100 caracteres)\\\"\\n" +
            "}\\n\\n" +
            
            "CULTIVO: '%s'\\n" +
            "VARIEDAD: '%s'\\n" +
            "Responde SOLO con el JSON, sin explicaciones adicionales.",
            nombreVariedad, nombreCultivo, nombreCultivo, nombreVariedad
        );
    }

    /**
     * Construye el prompt para sugerir variedades
     */
    private String construirPromptSugerencias(String nombreCultivo, String region) {
        return String.format(
            "Eres un agrónomo experto en variedades comerciales de cultivos en Perú.\\n\\n" +
            
            "VARIEDADES COMERCIALES REALES POR CULTIVO (Perú - INIA y comerciales):\\n\\n" +
            
            "MAÍZ: [\\\"INIA 619 Megahíbrido\\\", \\\"Marginal 28T\\\", \\\"PM-212\\\", \\\"Dekalb 7088\\\", \\\"Pioneer 30F35\\\"]\\n" +
            "PAPA: [\\\"Canchán\\\", \\\"Yungay\\\", \\\"Perricholi\\\", \\\"Única\\\", \\\"Amarilis\\\", \\\"Capiro\\\", \\\"Canchan INIA\\\"]\\n" +
            "ARROZ: [\\\"IR-43\\\", \\\"Tinajones\\\", \\\"La Puntilla\\\", \\\"Ferreñafe\\\", \\\"INIA 509\\\", \\\"NIR\\\", \\\"Capirona\\\"]\\n" +
            "QUINUA: [\\\"Salcedo INIA\\\", \\\"Pasankalla\\\", \\\"Blanca de Juli\\\", \\\"Illpa INIA\\\", \\\"Altiplano\\\"]\\n" +
            "TRIGO: [\\\"Centenario\\\", \\\"Andino\\\", \\\"INIA 433\\\", \\\"Molinero\\\"]\\n" +
            "FRIJOL: [\\\"Canario 2000\\\", \\\"Panamito\\\", \\\"Caballero\\\", \\\"Bayo Madero\\\"]\\n" +
            "CAMOTE: [\\\"Jonathan\\\", \\\"Huambachero\\\", \\\"INIA 100\\\", \\\"Costanero\\\"]\\n" +
            "YUCA: [\\\"Señorita\\\", \\\"Amarilla\\\", \\\"Blanca\\\", \\\"INIA 601\\\"]\\n" +
            "TOMATE: [\\\"Río Grande\\\", \\\"Santa Rosa\\\", \\\"Híbrido Arka\\\", \\\"Río Fuego\\\"]\\n" +
            "CEBOLLA: [\\\"Roja Arequipeña\\\", \\\"Roja Italiana\\\", \\\"Amarilla Dulce\\\", \\\"Victoria\\\"]\\n" +
            "ZANAHORIA: [\\\"Chantenay\\\", \\\"Nantes\\\", \\\"Imperator\\\"]\\n" +
            "AJÍ: [\\\"Panca\\\", \\\"Amarillo\\\", \\\"Limo\\\", \\\"Mirasol\\\"]\\n" +
            "CAFÉ: [\\\"Típica\\\", \\\"Caturra\\\", \\\"Catimor\\\", \\\"Bourbon\\\", \\\"Pache\\\"]\\n" +
            "CACAO: [\\\"CCN-51\\\", \\\"ICS-95\\\", \\\"Criollo\\\", \\\"Trinitario\\\"]\\n\\n" +
            
            "INSTRUCCIONES CRÍTICAS:\\n" +
            "1. Si el cultivo '%s' está en la lista, devuelve EXACTAMENTE esas variedades (máximo 5)\\n" +
            "2. Si no está en la lista pero conoces variedades reales, lista solo las verificables\\n" +
            "3. Si NO conoces variedades reales, devuelve un array vacío: []\\n" +
            "4. NO inventes nombres de variedades. Deben ser variedades comerciales reales\\n" +
            "5. Prioriza variedades del INIA Perú y comerciales conocidas\\n" +
            "6. La región '%s' puede influir pero no inventes variedades regionales\\n\\n" +
            
            "Responde SOLO con un array JSON de strings:\\n" +
            "[\\\"Variedad 1\\\", \\\"Variedad 2\\\", \\\"Variedad 3\\\", \\\"Variedad 4\\\", \\\"Variedad 5\\\"]\\n\\n" +
            
            "CULTIVO: '%s'\\n" +
            "REGIÓN: '%s'\\n" +
            "Responde SOLO con el array JSON, sin explicaciones adicionales.",
            nombreCultivo, region, nombreCultivo, region
        );
    }

    /**
     * Llama a la API de Groq
     */
    private String llamarGroqAPI(String prompt) throws Exception {
        if (groqApiKey == null || groqApiKey.isEmpty()) {
            log.error("Groq API key no configurada");
            throw new Exception("Groq API key no configurada. Por favor, configure la variable de entorno GROQ_API_KEY");
        }

        log.debug("Llamando a Groq API con modelo: meta-llama/llama-4-scout-17b-16e-instruct");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "meta-llama/llama-4-scout-17b-16e-instruct");
        requestBody.put("messages", List.of(
            Map.of("role", "user", "content", prompt)
        ));
        requestBody.put("temperature", 0.0);
        requestBody.put("max_tokens", 800);
        requestBody.put("top_p", 0.9);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                GROQ_API_URL,
                HttpMethod.POST,
                request,
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                String content = root.path("choices").get(0).path("message").path("content").asText();
                log.debug("Respuesta exitosa de Groq API");
                return content;
            } else {
                log.error("Respuesta no exitosa de Groq API: {}", response.getStatusCode());
                throw new Exception("Error en la respuesta de Groq API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error al llamar a Groq API: {}", e.getMessage(), e);
            throw new Exception("Error al comunicarse con Groq API: " + e.getMessage(), e);
        }
    }

    /**
     * Parsea la respuesta JSON de Groq
     */
    private Map<String, Object> parsearRespuestaJSON(String response) {
        try {
            String jsonClean = limpiarRespuesta(response);
            return objectMapper.readValue(jsonClean, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.error("Error al parsear respuesta JSON: {}", e.getMessage());
            return Map.of("error", "Error al parsear respuesta");
        }
    }

    /**
     * Parsea un array JSON de la respuesta
     */
    private List<String> parsearRespuestaArray(String response) {
        try {
            String jsonClean = limpiarRespuesta(response);
            return objectMapper.readValue(jsonClean, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            log.error("Error al parsear respuesta array: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Limpia la respuesta removiendo markdown si existe
     */
    private String limpiarRespuesta(String response) {
        String jsonClean = response.trim();
        if (jsonClean.startsWith("```json")) {
            jsonClean = jsonClean.substring(7);
        }
        if (jsonClean.startsWith("```")) {
            jsonClean = jsonClean.substring(3);
        }
        if (jsonClean.endsWith("```")) {
            jsonClean = jsonClean.substring(0, jsonClean.length() - 3);
        }
        return jsonClean.trim();
    }
}
