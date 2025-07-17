package com.jobinsight.proyectoretojobinsight.Service;

import com.jobinsight.proyectoretojobinsight.Modell.OfertaLaboral;
import com.jobinsight.proyectoretojobinsight.Repository.OfertaLaboralRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaLaboralService {
    private final OfertaLaboralRepository ofertaLaboralRepositorio;

    // Constructor
    public OfertaLaboralService(OfertaLaboralRepository ofertaLaboralRepositorio) {
        this.ofertaLaboralRepositorio = ofertaLaboralRepositorio;
    }

    // Obtener una oferta laboral por su ID
    public OfertaLaboral obtenerOfertaLaboralId(Integer id) {
        return ofertaLaboralRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Oferta laboral con ID " + id + " no encontrada"));
    }

    // Obtener todas las ofertas laborales
    public List<OfertaLaboral> obtenerOfertasLaborales() {
        return ofertaLaboralRepositorio.findAll();
    }

    // Crear una nueva oferta laboral
    public OfertaLaboral crearOfertaLaboral(OfertaLaboral nuevaOferta) {
        return ofertaLaboralRepositorio.save(nuevaOferta);
    }

    // Actualizar una oferta laboral existente
    public OfertaLaboral actualizarOfertaLaboral(Integer id, OfertaLaboral ofertaActualizar) {
        OfertaLaboral existente = obtenerOfertaLaboralId(id);

        existente.setTitulo(ofertaActualizar.getTitulo());
        existente.setDescripcion(ofertaActualizar.getDescripcion());
        existente.setRequisitos(ofertaActualizar.getRequisitos());
        existente.setUbicacion(ofertaActualizar.getUbicacion());
        existente.setFechaPublicacion(ofertaActualizar.getFechaPublicacion());
        existente.setSalario(ofertaActualizar.getSalario());
        existente.setTipoTrabajo(ofertaActualizar.getTipoTrabajo());

        return ofertaLaboralRepositorio.save(existente);
    }

    // Eliminar una oferta laboral por ID
    public void eliminarOfertaLaboral(Integer id) {
        if (!ofertaLaboralRepositorio.existsById(id)) {
            System.out.println("La oferta laboral no existe");
            return;
        }
        ofertaLaboralRepositorio.deleteById(id);
    }
}
