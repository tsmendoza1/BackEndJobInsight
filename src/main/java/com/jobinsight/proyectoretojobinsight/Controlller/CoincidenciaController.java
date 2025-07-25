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
        Coincidencia nuevaCoincidencia = coincidenciaServicio.crearCoincidencia(coincidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCoincidencia);
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
    public ResponseEntity<Coincidencia> obtenerCoincidencia(@PathVariable Long id) {
        Coincidencia coincidencia = coincidenciaServicio.obtenerCoincidenciaId(Math.toIntExact(id));
        return ResponseEntity.ok(coincidencia);
    }


}
