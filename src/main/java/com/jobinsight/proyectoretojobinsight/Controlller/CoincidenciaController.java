package com.jobinsight.proyectoretojobinsight.Controlller;

import com.jobinsight.proyectoretojobinsight.Modell.Coincidencia;
import com.jobinsight.proyectoretojobinsight.Service.CoincidenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar las operaciones relacionadas con las coincidencias entre candidatos y ofertas laborales.
 */
@Tag(name = "Coincidencia", description = "Operaciones relacionadas con las coincidencias entre candidatos y ofertas laborales")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/coincidencias")
public class CoincidenciaController {

    @Autowired
    private CoincidenciaService coincidenciaServicio;

    // Constructor
    public CoincidenciaController(CoincidenciaService coincidenciaServicio) {
        this.coincidenciaServicio = coincidenciaServicio;
    }

    @Operation(
            summary = "Permite crear una coincidencia entre un candidato y una oferta laboral",
            description = "Este método se encarga de crear una nueva coincidencia en la base de datos entre un candidato y una oferta laboral."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coincidencia creada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Solicitud incorrecta, error en los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Coincidencia> crearCoincidencia(@RequestBody Coincidencia coincidencia) {
        try {
            Coincidencia nuevaCoincidencia = coincidenciaServicio.crearCoincidencia(coincidencia);
            return ResponseEntity.ok(nuevaCoincidencia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Permite obtener una coincidencia por su ID",
            description = "Este método permite obtener los detalles de una coincidencia registrada en la base de datos a partir de su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coincidencia devuelta exitosamente"),
            @ApiResponse(responseCode = "404", description = "Coincidencia no encontrada con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Coincidencia> obtenerCoincidenciaPorId(
            @Parameter(description = "ID de la coincidencia que se obtendrá", required = true)
            @PathVariable Integer id) {
        try {
            Coincidencia coincidencia = coincidenciaServicio.obtenerCoincidenciaId(id);
            return ResponseEntity.ok(coincidencia);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Permite actualizar una coincidencia por su ID",
            description = "Este método permite actualizar los detalles de una coincidencia registrada en la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coincidencia actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Coincidencia no encontrada con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCoincidencia(
            @Parameter(description = "ID de la coincidencia que se actualizará", required = true)
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la coincidencia",
                    required = true
            )
            @RequestBody Coincidencia coincidenciaActualizar) {
        try {
            Coincidencia coincidenciaActualizada = coincidenciaServicio.actualizarCoincidencia(id, coincidenciaActualizar);
            return ResponseEntity.ok(coincidenciaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error actualizando la coincidencia: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Permite eliminar una coincidencia por su ID",
            description = "Este método permite eliminar una coincidencia de la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coincidencia eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Coincidencia no encontrada con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCoincidencia(
            @Parameter(description = "ID de la coincidencia que se eliminará", required = true)
            @PathVariable Integer id) {
        try {
            coincidenciaServicio.eliminarCoincidencia(id);
            return ResponseEntity.ok("Coincidencia eliminada exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error eliminando la coincidencia: " + e.getMessage());
        }
    }
}
