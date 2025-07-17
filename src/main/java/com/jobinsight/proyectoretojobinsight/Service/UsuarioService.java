package com.jobinsight.proyectoretojobinsight.Service;

import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import com.jobinsight.proyectoretojobinsight.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepositorio;

    public UsuarioService(UsuarioRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Obtener un usuario por su ID
    public Usuario obtenerUsuarioId(Integer id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepositorio.findAll();
    }

    // Crear un nuevo usuario
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return usuarioRepositorio.save(nuevoUsuario);
    }

    // Actualizar un usuario existente
    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizar) {
        Usuario existente = obtenerUsuarioId(id);

        existente.setNombre(usuarioActualizar.getNombre());
        existente.setApellido(usuarioActualizar.getApellido());
        existente.setEmail(usuarioActualizar.getEmail());
        existente.setContraseña(usuarioActualizar.getContraseña());
        existente.setTelefono(usuarioActualizar.getTelefono());
        existente.setFechaCreacion(usuarioActualizar.getFechaCreacion());
        existente.setTipo(usuarioActualizar.getTipo());
        existente.setDireccion(usuarioActualizar.getDireccion());

        return usuarioRepositorio.save(existente);
    }

    // Eliminar un usuario por ID
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepositorio.existsById(id)) {
            System.out.println("El usuario no existe");
            return;
        }
        usuarioRepositorio.deleteById(id);
    }
}
