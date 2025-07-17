package com.jobinsight.proyectoretojobinsight.Modell;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Schema(description = "Entidad que representa la coincidencia entre un candidato y una oferta laboral en la plataforma JobInsight")
public class Coincidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la coincidencia", example = "1")
    private Long id;


    @Schema(description = "Identificador de la oferta laboral asociada a esta coincidencia", example = "1")
    private Long ofertaId;  // Relación con la OfertaLaboral

    @Schema(description = "Puntuación de coincidencia entre el perfil del candidato y la oferta laboral", example = "0.85")
    private Double puntuacion;  // Puntuación de coincidencia entre el perfil y la oferta

    @Schema(description = "Fecha en la que se calcula la coincidencia", example = "2025-06-25")
    private LocalDate fechaCoincidencia;  // Fecha de la coincidencia

    // Relación con la oferta laboral
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_laboral_id")
    @Schema(description = "Oferta laboral asociada a esta coincidencia")
    private OfertaLaboral ofertaLaboral;  // Relación con la oferta laboral

    // Relación con el candidato (puede ser una relación con la clase Usuario o Candidato)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id")
    @Schema(description = "Candidato asociado a esta coincidencia")
    private Usuario candidato;  // Relación con el Candidato

    public Coincidencia (){

    }

    // Constructor sin id (para crear nuevas coincidencias)
    public Coincidencia( Long ofertaId, Double puntuacion, LocalDate fechaCoincidencia, OfertaLaboral ofertaLaboral, Usuario candidato) {
        this.ofertaId = ofertaId;
        this.puntuacion = puntuacion;
        this.fechaCoincidencia = fechaCoincidencia;
        this.ofertaLaboral = ofertaLaboral;
        this.candidato = candidato;
    }

    // Constructor con id (para actualizar coincidencias existentes)
    public Coincidencia(Long id, Long ofertaId, Double puntuacion, LocalDate fechaCoincidencia, OfertaLaboral ofertaLaboral, Usuario candidato) {
        this.id = id;
        this.ofertaId = ofertaId;
        this.puntuacion = puntuacion;
        this.fechaCoincidencia = fechaCoincidencia;
        this.ofertaLaboral = ofertaLaboral;
        this.candidato = candidato;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDate getFechaCoincidencia() {
        return fechaCoincidencia;
    }

    public void setFechaCoincidencia(LocalDate fechaCoincidencia) {
        this.fechaCoincidencia = fechaCoincidencia;
    }

    public OfertaLaboral getOfertaLaboral() {
        return ofertaLaboral;
    }

    public void setOfertaLaboral(OfertaLaboral ofertaLaboral) {
        this.ofertaLaboral = ofertaLaboral;
    }

    public Usuario getCandidato() {
        return candidato;
    }

    public void setCandidato(Usuario candidato) {
        this.candidato = candidato;
    }
}
