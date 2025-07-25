package com.jobinsight.proyectoretojobinsight.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class NlqService {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    public NlqService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.objectMapper = new ObjectMapper(); // para leer JSON manualmente
    }

    public Object answer(String pregunta) {
        System.out.println("ENTRANDO EN LA AI prompt");
        String prompt = """
            Eres un asistente que transforma preguntas en lenguaje natural a JPQL (Java Persistence Query Language).
            Usa las siguientes entidades con sus atributos:
            
            Entidad 'Usuario':
            id (Long)
            nombre (String)
            email (String)
            password (String)
            tipo (enum TipoUsuario)
            perfil (Perfil)
            
            Entidad 'Perfil':
            id (Long)
            usuario (Usuario)
            nombreCompleto (String)
            telefono (String)
            direccion (String)
            fechaNacimiento (LocalDate)
            postulaciones (List<Postulacion>)
            
            Entidad 'OfertaLaboral':
            id (Long)
            nombreEmpresa (String)
            titulo (String)
            descripcion (String)
            requisitos (List<String>)
            requisitosExperiencia (List<String>)
            ubicacion (String)
            fechaPublicacion (LocalDate)
            salario (double)
            tipoTrabajo (enum TipoTrabajo)
            
            Entidad 'Postulacion':
            id (Long)
            perfil (Perfil)
            oferta (OfertaLaboral)
            estado (enum EstadoPostulacion)
            fechaPostulacion (LocalDate)
            
            Dado un enunciado en lenguaje natural, devuelve solo la consulta JPQL válida para responderlo, sin explicación ni formato adicional.
            
        Pregunta: %s
        """.formatted(pregunta);
        System.out.println("SALIENDO EN LA AI prompt");

        try {
            System.out.println("ENTRANDO EN LA AI TRY");

            WebClient client = WebClient.builder()
                    .baseUrl("https://openrouter.ai")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            System.out.println("client");

            String responseJson = client.post()
                    .uri("/api/v1/chat/completions")
                    .bodyValue("""
            {
              "model": "openai/gpt-4.1",
              "messages": [
                {"role": "user", "content": "%s"}
              ],
              "max_tokens": 1000
            }
            """.formatted(prompt))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        System.err.println("Error del servidor OpenRouter:");
                                        System.err.println(errorBody);
                                        return Mono.error(new RuntimeException("Error al llamar a OpenRouter: " + errorBody));
                                    }))
                    .bodyToMono(String.class)
                    .block();

            System.out.println("responseJson");

            Map<String, Object> response = objectMapper.readValue(responseJson, Map.class);
            Map<?, ?> choice = (Map<?, ?>) ((List<?>) response.get("choices")).get(0);
            Map<?, ?> message = (Map<?, ?>) choice.get("message");
            String jpql = message.get("content").toString().trim();

            System.out.println("JPQL generado por la IA: " + jpql);

            try {
                Query query = entityManager.createQuery(jpql);
                Object result = query.getResultList();

                // Si es lista vacía, devolver lista vacía
                if (result == null) {
                    return List.of();
                }

                // Devolver resultado tal cual (puede ser lista de mapas, lista de Longs, etc)
                return result;

            } catch (Exception e) {
                return Map.of(
                        "error", e.getMessage(),
                        "jpql", jpql
                );
            }

        } catch (Exception e) {
            return Map.of(
                    "error", e.getMessage()
            );
        }
    }
}
