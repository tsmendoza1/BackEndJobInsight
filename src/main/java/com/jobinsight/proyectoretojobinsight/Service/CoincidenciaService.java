package com.jobinsight.proyectoretojobinsight.Service;


import com.jobinsight.proyectoretojobinsight.Modell.AlgoritmoCoincidencia;
import com.jobinsight.proyectoretojobinsight.Modell.Coincidencia;
import com.jobinsight.proyectoretojobinsight.Modell.OfertaLaboral;
import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import com.jobinsight.proyectoretojobinsight.Repository.CoincidenciaRepository;
import com.jobinsight.proyectoretojobinsight.Repository.OfertaLaboralRepository;
import com.jobinsight.proyectoretojobinsight.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoincidenciaService {

    private final AlgoritmoCoincidencia algoritmoCoincidencia;
    private final CoincidenciaRepository coincidenciaRepositorio;
    private final UsuarioRepository usuarioRepositorio;
    private final OfertaLaboralRepository ofertaLaboralRepositorio;

    // Constructor
    public CoincidenciaService(CoincidenciaRepository coincidenciaRepositorio,
                                UsuarioRepository usuarioRepositorio,
                                OfertaLaboralRepository ofertaLaboralRepositorio) {
        this.coincidenciaRepositorio = coincidenciaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.ofertaLaboralRepositorio = ofertaLaboralRepositorio;
        this.algoritmoCoincidencia = new AlgoritmoCoincidencia(); // Inicializamos el algoritmo
    }

    // Obtener una coincidencia por su ID
    public Coincidencia obtenerCoincidenciaId(Integer id) {
        return coincidenciaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coincidencia con ID " + id + " no encontrada"));
    }

    // Obtener todas las coincidencias
    public List<Coincidencia> obtenerCoincidencias() {
        return coincidenciaRepositorio.findAll();
    }

    // Crear una nueva coincidencia
    public Coincidencia crearCoincidencia(Coincidencia nuevaCoincidencia) {
        // Asocia la coincidencia con el candidato (usuario)
        Usuario candidato = usuarioRepositorio.findById(Math.toIntExact(nuevaCoincidencia.getCandidato().getId()))
                .orElseThrow(() -> new EntityNotFoundException("Candidato no encontrado"));

        nuevaCoincidencia.setCandidato(candidato);

        // Asocia la coincidencia con la oferta laboral
        OfertaLaboral ofertaLaboral = ofertaLaboralRepositorio.findById(Math.toIntExact(nuevaCoincidencia.getOfertaLaboral().getId()))
                .orElseThrow(() -> new EntityNotFoundException("Oferta laboral no encontrada"));

        nuevaCoincidencia.setOfertaLaboral(ofertaLaboral);

        // Guardar la coincidencia
        return coincidenciaRepositorio.save(nuevaCoincidencia);
    }


    public void crearCoincidenciasParaOferta(OfertaLaboral ofertaLaboral) {
        List<Usuario> candidatos = usuarioRepositorio.findAll(); // Obtener todos los candidatos
        List<Coincidencia> coincidencias = algoritmoCoincidencia.calcularCoincidencias(ofertaLaboral, candidatos);

        for (Coincidencia coincidencia : coincidencias) {
            coincidenciaRepositorio.save(coincidencia); // Guardar la coincidencia en la base de datos
        }
    }

    // Actualizar una coincidencia existente
    public Coincidencia actualizarCoincidencia(Integer id, Coincidencia coincidenciaActualizar) {
        Coincidencia existente = obtenerCoincidenciaId(id);

        // Actualizar la puntuación y fecha de coincidencia
        existente.setPuntuacion(coincidenciaActualizar.getPuntuacion());
        existente.setFechaCoincidencia(coincidenciaActualizar.getFechaCoincidencia());

        // Actualizar las relaciones
        existente.setCandidato(coincidenciaActualizar.getCandidato());
        existente.setOfertaLaboral(coincidenciaActualizar.getOfertaLaboral());

        return coincidenciaRepositorio.save(existente);
    }

    // Eliminar una coincidencia por ID
    public void eliminarCoincidencia(Integer id) {
        if (!coincidenciaRepositorio.existsById(id)) {
            System.out.println("La coincidencia no existe");
            return;
        }
        coincidenciaRepositorio.deleteById(id);
    }
}
