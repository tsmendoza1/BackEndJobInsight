package com.jobinsight.proyectoretojobinsight.Modell;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Schema(description = "Entidad que representa el estado de postulacion del candidato")

public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID único de la postulación

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    private Perfil perfil;  // Relación con la entidad Perfil (Candidato que se postula)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oferta_id", referencedColumnName = "id")
    private OfertaLaboral oferta;  // Relación con la entidad OfertaLaboral (Oferta a la que se postula)

    @Enumerated(EnumType.STRING)
    private EstadoPostulacion estado;  // Estado de la postulación (EN_PROCESO, ACEPTADA, RECHAZADA)

    private LocalDate fechaPostulacion;  // Fecha de la postulación

    // Constructor por defecto
    public Postulacion() {
    }

    // Constructor con parámetros
    public Postulacion(Perfil perfil, OfertaLaboral oferta, EstadoPostulacion estado, LocalDate fechaPostulacion) {
        this.perfil = perfil;
        this.oferta = oferta;
        this.estado = estado;
        this.fechaPostulacion = fechaPostulacion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public OfertaLaboral getOferta() {
        return oferta;
    }

    public void setOferta(OfertaLaboral oferta) {
        this.oferta = oferta;
    }

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    // Método para verificar si el candidato ya se ha postulado a la misma oferta
    public boolean mismaOfertaYPerfil(OfertaLaboral oferta, Perfil perfil) {
        return this.oferta.equals(oferta) && this.perfil.equals(perfil);
    }
}
