package com.jobinsight.proyectoretojobinsight.Modell;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import reactor.util.annotation.NonNull;

import java.time.LocalDate;

@Entity
@Schema(description = "Entidad que representa la formación académica de un usuario en la plataforma JobInsight")
public class FormacionAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la formación", example = "1")
    private Long id;

    @Schema(description = "Nombre de la institución educativa", example = "Universidad de Tecnología")
    private String institucion;

    @Schema(description = "Título obtenido en la institución", example = "Ingeniería de Software")
    private String titulo;

    @Schema(description = "Fecha de graduación", example = "2025-06-25")
    private LocalDate fechaGraduacion;

    // Relación con el Perfil (ya que cada formación pertenece a un perfil de usuario)
    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonBackReference
    @Schema(description = "Perfil asociado a esta formación", example = "1")
    private Perfil perfil; //Relacion con la clase perfil


    public FormacionAcademica() {

    }

    // Constructor sin id (para crear nuevos registros de formación)
    public FormacionAcademica(@NonNull String institucion, @NonNull String titulo, @NonNull LocalDate fechaGraduacion, Perfil perfil) {
        this.institucion = institucion;
        this.titulo = titulo;
        this.fechaGraduacion = fechaGraduacion;
        this.perfil = perfil;
    }

    // Constructor con id (para actualizar registros existentes)
    public FormacionAcademica(Long id, @NonNull String institucion, @NonNull String titulo, @NonNull LocalDate fechaGraduacion, Perfil perfil) {
        this.id = id;
        this.institucion = institucion;
        this.titulo = titulo;
        this.fechaGraduacion = fechaGraduacion;
        this.perfil = perfil;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaGraduacion() {
        return fechaGraduacion;
    }

    public void setFechaGraduacion(LocalDate fechaGraduacion) {
        this.fechaGraduacion = fechaGraduacion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}