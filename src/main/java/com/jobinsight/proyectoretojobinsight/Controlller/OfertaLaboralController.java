package com.jobinsight.proyectoretojobinsight.Controlller;

import com.jobinsight.proyectoretojobinsight.Modell.OfertaLaboral;
import com.jobinsight.proyectoretojobinsight.Service.OfertaLaboralService;
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

import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con las ofertas laborales.
 */
@Tag(name = "Oferta Laboral", description = "Operaciones relacionadas con ofertas laborales")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ofertas")
public class OfertaLaboralController {

    @Autowired
    private OfertaLaboralService ofertaLaboralServicio;


    public OfertaLaboralController(OfertaLaboralService ofertaLaboralServicio) {
        this.ofertaLaboralServicio = ofertaLaboralServicio;
    }

    @Operation(
            summary = "Obtiene todas las ofertas laborales",
            description = "Devuelve una lista con todas las ofertas laborales registradas en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ofertas laborales devuelta exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<OfertaLaboral>> obtenerTodasLasOfertas() {
        try {
            List<OfertaLaboral> ofertas = ofertaLaboralServicio.obtenerTodasLasOfertas();
            return ResponseEntity.ok(ofertas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(
            summary = "Permite crear una oferta laboral",
            description = "Este método se encarga de crear una nueva oferta laboral en la base de datos, asignando un ID único automáticamente. Devuelve la oferta laboral registrada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oferta laboral creada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Solicitud incorrecta, error en los datos enviados")
    })
    @PostMapping
    public ResponseEntity<OfertaLaboral> crearOfertaLaboral(@RequestBody OfertaLaboral ofertaLaboral) {
        try {
            OfertaLaboral nuevaOferta = ofertaLaboralServicio.crearOfertaLaboral(ofertaLaboral);
            return ResponseEntity.ok(nuevaOferta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Permite obtener una oferta laboral por su ID",
            description = "Este método permite obtener los detalles de una oferta laboral registrada en la base de datos a partir de su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oferta laboral devuelta exitosamente"),
            @ApiResponse(responseCode = "404", description = "Oferta laboral no encontrada con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OfertaLaboral> obtenerOfertaLaboralPorId(
            @Parameter(description = "ID de la oferta laboral que se obtendrá", required = true)
            @PathVariable Integer id) {
        try {
            OfertaLaboral ofertaLaboral = ofertaLaboralServicio.obtenerOfertaLaboralId(id);
            return ResponseEntity.ok(ofertaLaboral);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Permite actualizar una oferta laboral por su ID",
            description = "Este método permite actualizar los detalles de una oferta laboral registrada en la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oferta laboral actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Oferta laboral no encontrada con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOfertaLaboral(
            @Parameter(description = "ID de la oferta laboral que se actualizará", required = true)
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la oferta laboral",
                    required = true
            )
            @RequestBody OfertaLaboral ofertaLaboralActualizar) {
        try {
            OfertaLaboral ofertaActualizada = ofertaLaboralServicio.actualizarOfertaLaboral(id, ofertaLaboralActualizar);
            return ResponseEntity.ok(ofertaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error actualizando la oferta laboral: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Permite eliminar una oferta laboral por su ID",
            description = "Este método permite eliminar una oferta laboral de la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oferta laboral eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Oferta laboral no encontrada con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOfertaLaboral(
            @Parameter(description = "ID de la oferta laboral que se eliminará", required = true)
            @PathVariable Integer id) {
        try {
            ofertaLaboralServicio.eliminarOfertaLaboral(id);
            return ResponseEntity.ok("Oferta laboral eliminada exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error eliminando la oferta laboral: " + e.getMessage());
        }
    }
}