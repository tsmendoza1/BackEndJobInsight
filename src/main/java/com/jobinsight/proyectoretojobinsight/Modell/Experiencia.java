package com.jobinsight.proyectoretojobinsight.Modell;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Schema(description = "Entidad que representa la experiencia laboral de un usuario en la plataforma JobInsight")
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la experiencia", example = "1")
    private Long id;

    @Schema(description = "Nombre de la empresa donde el candidato trabajó", example = "Tech Solutions")
    private String empresa;  // Empresa donde el candidato trabajó

    @Schema(description = "Puesto que ocupó el candidato en la empresa", example = "Desarrollador Java")
    private String puesto;  // Puesto ocupado por el candidato

    @Schema(description = "Fecha de inicio de la experiencia laboral", example = "2020-06-01")
    private LocalDate fechaInicio;  // Fecha de inicio de la experiencia laboral

    @Schema(description = "Fecha de fin de la experiencia laboral (puede ser null si sigue vigente)", example = "2022-06-01")
    private LocalDate fechaFin;  // Fecha de finalización de la experiencia laboral

    @Schema(description = "Descripción de las responsabilidades del candidato en su puesto", example = "Desarrollo de aplicaciones Java utilizando Spring Boot.")
    private String descripcion;  // Descripción de las responsabilidades

    // Relación con el Perfil (porque cada experiencia pertenece a un perfil de usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    @Schema(description = "Perfil asociado a esta experiencia laboral")
    private Perfil perfil;  // Relación con la clase Perfil

    public Experiencia(){

    }

    // Constructor sin id (para crear nuevas experiencias laborales)
    public Experiencia(String empresa, String puesto, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, Perfil perfil) {
        this.empresa = empresa;
        this.puesto = puesto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.perfil = perfil;
    }

    // Constructor con id (para actualizar experiencias laborales existentes)
    public Experiencia(Long id, String empresa, String puesto, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, Perfil perfil) {
        this.id = id;
        this.empresa = empresa;
        this.puesto = puesto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.perfil = perfil;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}