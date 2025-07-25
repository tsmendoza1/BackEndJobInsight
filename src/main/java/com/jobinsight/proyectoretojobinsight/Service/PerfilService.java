package com.jobinsight.proyectoretojobinsight.Service;

import com.jobinsight.proyectoretojobinsight.DataTransferObject.PerfilDTO;
import com.jobinsight.proyectoretojobinsight.DataTransferObject.UsuarioDTO;
import com.jobinsight.proyectoretojobinsight.Modell.Experiencia;
import com.jobinsight.proyectoretojobinsight.Modell.FormacionAcademica;
import com.jobinsight.proyectoretojobinsight.Modell.Perfil;
import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import com.jobinsight.proyectoretojobinsight.Repository.ExperienciaRepository;
import com.jobinsight.proyectoretojobinsight.Repository.FormacionAcademicaRepository;
import com.jobinsight.proyectoretojobinsight.Repository.PerfilRepository;
import com.jobinsight.proyectoretojobinsight.Repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepositorio;
    private final ExperienciaRepository experienciaRepositorio;
    private final FormacionAcademicaRepository formacionRepositorio;
    private final UsuarioRepository usuarioRepositorio;

    // Constructor
    public PerfilService(PerfilRepository perfilRepositorio,
                         ExperienciaRepository experienciaRepositorio,
                         FormacionAcademicaRepository formacionRepositorio,
                         UsuarioRepository usuarioRepositorio) {
        this.perfilRepositorio = perfilRepositorio;
        this.experienciaRepositorio = experienciaRepositorio;
        this.formacionRepositorio = formacionRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public String extraerCorreoDesdeJWT(String token) {
        return Jwts.parser()
                .setSigningKey("JobinsightSuenaChido2025".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Perfil obtenerPerfilPorCorreoUsuario(String correo) {
        Optional<Usuario> optionalUsuario = usuarioRepositorio.findByEmail(correo);
        Usuario usuario = optionalUsuario.orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado con correo: " + correo)
        );

        Optional<Perfil> optionalPerfil = perfilRepositorio.findByUsuario(usuario);
        return optionalPerfil.orElseThrow(
                () -> new EntityNotFoundException("Perfil no encontrado para el usuario con correo: " + correo)
        );
    }

//.orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado para el usuario"));
    // Obtener un perfil por su ID
    public Perfil obtenerPerfilId(Integer id) {
        return perfilRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil con ID " + id + " no encontrado"));
    }

    // Obtener todos los perfiles
    public List<Perfil> obtenerPerfiles() {
        return perfilRepositorio.findAll();
    }

    // Crear un nuevo perfil
    public Perfil crearPerfil(Perfil nuevoPerfil) {
        // Guardar el perfil primero para obtener el ID generado
        Perfil perfilGuardado = perfilRepositorio.save(nuevoPerfil);

        // Asignar el perfil a las experiencias relacionadas y guardarlas
        if (nuevoPerfil.getExperienciaLaboral() != null) {
            for (Experiencia experiencia : nuevoPerfil.getExperienciaLaboral()) {
                experiencia.setPerfil(perfilGuardado); // Establecer la relación con el perfil guardado
                experienciaRepositorio.save(experiencia); // Guardar la experiencia laboral
            }
        }

        // Asignar el perfil a las formaciones académicas relacionadas y guardarlas
        if (nuevoPerfil.getFormacionAcademica() != null) {
            for (FormacionAcademica formacion : nuevoPerfil.getFormacionAcademica()) {
                formacion.setPerfil(perfilGuardado); // Establecer la relación con el perfil guardado
                formacionRepositorio.save(formacion); // Guardar la formación académica
            }
        }

        return perfilGuardado; // Devolver el perfil guardado
    }

    // Actualizar un perfil existente
    public Perfil actualizarPerfil(Integer id, Perfil perfilActualizar) {
        // Obtener el perfil existente
        Perfil perfilExistente = obtenerPerfilId(id);

        // Actualizar los atributos del perfil
        perfilExistente.setHabilidades(perfilActualizar.getHabilidades());
        perfilExistente.setDescripcion(perfilActualizar.getDescripcion());

        // Actualizar experiencias
        if (perfilActualizar.getExperienciaLaboral() != null) {
            for (Experiencia experiencia : perfilActualizar.getExperienciaLaboral()) {
                experiencia.setPerfil(perfilExistente); // Asocia la experiencia al perfil
                experienciaRepositorio.save(experiencia); // Guardar la experiencia
            }
        }

        // Actualizar formaciones
        if (perfilActualizar.getFormacionAcademica() != null) {
            for (FormacionAcademica formacion : perfilActualizar.getFormacionAcademica()) {
                formacion.setPerfil(perfilExistente); // Asocia la formación al perfil
                formacionRepositorio.save(formacion); // Guardar la formación
            }
        }

        // Guardar el perfil actualizado
        return perfilRepositorio.save(perfilExistente);
    }

    // Eliminar un perfil por ID
    public void eliminarPerfil(Integer id) {
        if (!perfilRepositorio.existsById(id)) {
            System.out.println("El perfil no existe");
            return;
        }
        perfilRepositorio.deleteById(id);
    }

    //

    public PerfilDTO convertirAPerfilDTO(Perfil perfil) {
        if (perfil == null) {
            return null;
        }

        Usuario usuario = perfil.getUsuario();
        UsuarioDTO usuarioDTO = null;

        if (usuario != null) {
            usuarioDTO = new UsuarioDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getTelefono(),
                    usuario.getDireccion(),
                    usuario.getTipo(),
                    usuario.getFechaCreacion() != null ? usuario.getFechaCreacion().toString() : null
            );
        }

        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setId(perfil.getId());
        perfilDTO.setUsuario(usuarioDTO);
        perfilDTO.setHabilidades(perfil.getHabilidades());
        perfilDTO.setDescripcion(perfil.getDescripcion());
        perfilDTO.setExperienciaLaboral(perfil.getExperienciaLaboral());
        perfilDTO.setFormacionAcademica(perfil.getFormacionAcademica());

        return perfilDTO;
    }

}
