package com.jobinsight.proyectoretojobinsight.Controlller;

import com.jobinsight.proyectoretojobinsight.Modell.Perfil;
import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import com.jobinsight.proyectoretojobinsight.Security.UsuarioDetailsAdapter;
import com.jobinsight.proyectoretojobinsight.Service.PerfilService;
import com.jobinsight.proyectoretojobinsight.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Usuario", description = "Operaciones Relacionadas con Usuarios")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServicio;

    @Autowired
    private PerfilService perfilServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioServicio, PerfilService perfilServicio) {
        this.usuarioServicio = usuarioServicio;
        this.perfilServicio = perfilServicio;
    }

    @Operation(
            summary = "Permite crear un usuario con un ID único",
            description = "Este método se encarga de crear un nuevo usuario en la base de datos, asignando un ID único automáticamente. Devuelve el usuario registrado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Solicitud incorrecta, error en los datos enviados")
    })
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario nuevoUsuario) {
        if (nuevoUsuario.getPassword() == null || nuevoUsuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía.");
        }

        nuevoUsuario.setFechaCreacion(LocalDate.now());
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));

        Usuario usuarioGuardado = usuarioServicio.crearUsuario(nuevoUsuario);

        // Crear perfil asociado y asignar relación bidireccional
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuarioGuardado);
        perfil.setHabilidades(List.of());
        perfil.setDescripcion("");
        perfil.setExperienciaLaboral(List.of());
        perfil.setFormacionAcademica(List.of());

        Perfil perfilGuardado = perfilServicio.crearPerfil(perfil);

        // Asignar el perfil creado al usuario y actualizar
        usuarioGuardado.setPerfil(perfilGuardado);

        return usuarioGuardado;
    }

    @Operation(
            summary = "Permite obtener un usuario según el ID",
            description = "Este método permite obtener los detalles de un usuario registrado en la base de datos a partir de su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario devuelto exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario que se obtendrá", required = true)
            @PathVariable Integer id) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioId(id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            summary = "Permite actualizar un usuario por el ID",
            description = "Este método permite actualizar los detalles de un usuario ya registrado en la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @Parameter(description = "ID del usuario que se actualizará", required = true)
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del usuario",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Usuario.class))
            )
            @RequestBody Usuario usuarioActualizar) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, usuarioActualizar);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error actualizando el usuario: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Permite eliminar un usuario por su ID",
            description = "Este método permite eliminar un usuario de la base de datos utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en los datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(
            @Parameter(description = "ID del usuario que se eliminará", required = true)
            @PathVariable Integer id) {
        try {
            usuarioServicio.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error eliminando el usuario: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioActual() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UsuarioDetailsAdapter usuarioDetails) {
            Usuario usuario = usuarioServicio.obtenerUsuarioId((int) usuarioDetails.getId());
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
