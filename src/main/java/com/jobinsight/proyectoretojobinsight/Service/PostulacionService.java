package com.jobinsight.proyectoretojobinsight.Service;

import com.jobinsight.proyectoretojobinsight.Modell.EstadoPostulacion;
import com.jobinsight.proyectoretojobinsight.Modell.Perfil;
import com.jobinsight.proyectoretojobinsight.Modell.Postulacion;
import com.jobinsight.proyectoretojobinsight.Repository.PostulacionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostulacionService {
    @Autowired
    private PostulacionRepository postulacionRepository;

    public Postulacion crearPostulacion(Postulacion postulacion) {
        // Validación para evitar que el candidato se postule dos veces a la misma oferta
        if (postulacionRepository.existsByPerfilAndOferta(postulacion.getPerfil(), postulacion.getOferta())) {
            throw new IllegalArgumentException("Ya te has postulado a esta oferta.");
        }
        return postulacionRepository.save(postulacion);  // Guardamos la postulación
    }

    public Postulacion cambiarEstadoPostulacion(Long id, EstadoPostulacion nuevoEstado) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Postulación no encontrada"));
        postulacion.setEstado(nuevoEstado);
        return postulacionRepository.save(postulacion);  // Actualizamos el estado de la postulación
    }

    public List<Postulacion> obtenerPostulacionesPorPerfil(Perfil perfil) {
        return postulacionRepository.findByPerfil(perfil); // Obtener postulaciones de un candidato
    }
}
