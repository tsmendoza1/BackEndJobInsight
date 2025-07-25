package com.jobinsight.proyectoretojobinsight.Controlller;

import com.jobinsight.proyectoretojobinsight.DataTransferObject.PerfilDTO;
import com.jobinsight.proyectoretojobinsight.Modell.Perfil;
import com.jobinsight.proyectoretojobinsight.Service.PerfilService;
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
 * Controlador para gestionar las operaciones relacionadas con los perfiles de los usuarios.
 */
@Tag(name = "Perfil", description = "Operaciones Relacionadas con Perfiles")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {
    @Autowired
    private PerfilService perfilServicio;

    public PerfilController(PerfilService perfilServicio) {
        this.perfilServicio = perfilServicio;
    }

    @Operation(
            summary = "Obtener perfil del usuario autenticado",
            description = "Devuelve los datos del perfil según el token JWT del usuario autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/mi-perfil")
    public ResponseEntity<?> obtenerMiPerfil(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            String correoUsuario = perfilServicio.extraerCorreoDesdeJWT(jwt); // Necesitas implementarlo
            Perfil perfil = perfilServicio.obtenerPerfilPorCorreoUsuario(correoUsuario); // También
            PerfilDTO dto = perfilServicio.convertirAPerfilDTO(perfil);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @Operation(
            summary = "Permite crear un perfil con un ID único",
            description = "Este método se encarga de crear un nuevo perfil en la base de datos, asignando un ID único automáticamente. Devuelve el perfil registrado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Solicitud incorrecta, error en los datos enviados")
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Perfil> crearPerfil(@RequestBody Perfil nuevoPerfil) {
        Perfil perfilCreado = perfilServicio.crearPerfil(nuevoPerfil);
        return new ResponseEntity<>(perfilCreado, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Permite obtener un perfil según el ID",
            description = "Este método permite obtener los detalles de un perfil registrado en la base de datos a partir de su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil devuelto exitosamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfilPorId(
            @Parameter(description = "ID del perfil que se obtendrá", required = true)
            @PathVariable Integer id) {
        try {
            Perfil perfil = perfilServicio.obtenerPerfilId(id);
            PerfilDTO perfilDTO = perfilServicio.convertirAPerfilDTO(perfil);
            return ResponseEntity.ok(perfilDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @Operation(
            summary = "Permite actualizar un perfil por el ID",
            description = "Este método permite actualizar los detalles de un perfil ya registrado en la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerfil(
            @Parameter(description = "ID del perfil que se actualizará", required = true)
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del perfil",
                    required = true
            )
            @RequestBody Perfil perfilActualizar) {
        try {
            Perfil perfilActualizado = perfilServicio.actualizarPerfil(id, perfilActualizar);
            return ResponseEntity.ok(perfilActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error actualizando el perfil: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Permite eliminar un perfil por su ID",
            description = "Este método permite eliminar un perfil de la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPerfil(
            @Parameter(description = "ID del perfil que se eliminará", required = true)
            @PathVariable Integer id) {
        try {
            perfilServicio.eliminarPerfil(id);
            return ResponseEntity.ok("Perfil eliminado exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error eliminando el perfil: " + e.getMessage());
        }
    }
}
