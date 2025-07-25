package com.jobinsight.proyectoretojobinsight.Modell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Schema(description = "Entidad que representa el perfil de un usuario en la plataforma JobInsight")

public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del perfil", example = "1")
    private Long id;

    @Schema(description = "Identificador único del usuario asociado al perfil", example = "1")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ElementCollection
    @Schema(description = "Lista de habilidades del candidato")
    private List<String> habilidades;

    @Schema(description = "Descripción adicional sobre el perfil del usuario")
    private String descripcion;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Lista de experiencias laborales del usuario", example = "[\"Desarrollador Backend\", \"Desarrollador Frontend\"]")
    private List<Experiencia> experienciaLaboral;

    @Schema(description = "Lista de formaciones académicas del candidato")
    @OneToMany(mappedBy = "perfil")
    @JsonManagedReference
    private List<FormacionAcademica> formacionAcademica;

    public Perfil() {

    }

    // Constructor sin id (para crear un nuevo perfil)
    public Perfil(List<String> habilidades, List<Experiencia> experienciaLaboral, List<FormacionAcademica> formacionAcademica, String descripcion, Usuario usuario) {
        this.habilidades = habilidades;
        this.experienciaLaboral = experienciaLaboral;
        this.formacionAcademica = formacionAcademica;
        this.descripcion = descripcion;
        this.usuario = usuario; // Asociamos al `Usuario` correspondiente
    }

    // Constructor con id (para actualizar un perfil existente)
    public Perfil(Long id, List<String> habilidades, List<Experiencia> experienciaLaboral, List<FormacionAcademica> formacionAcademica, String descripcion, Usuario usuario) {
        this.id = id;
        this.habilidades = habilidades;
        this.experienciaLaboral = experienciaLaboral;
        this.formacionAcademica = formacionAcademica;
        this.descripcion = descripcion;
        this.usuario = usuario; // Asociamos al `Usuario` correspondiente
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    public List<Experiencia> getExperienciaLaboral() {
        return experienciaLaboral;
    }

    public void setExperienciaLaboral(List<Experiencia> experienciaLaboral) {
        this.experienciaLaboral = experienciaLaboral;
    }

    public List<FormacionAcademica> getFormacionAcademica() {
        return formacionAcademica;
    }

    public void setFormacionAcademica(List<FormacionAcademica> formacionAcademica) {
        this.formacionAcademica = formacionAcademica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
