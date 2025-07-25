package com.jobinsight.proyectoretojobinsight.DataTransferObject;

import com.jobinsight.proyectoretojobinsight.Modell.Experiencia;
import com.jobinsight.proyectoretojobinsight.Modell.FormacionAcademica;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PerfilDTO {

    @Schema(description = "Identificador único del perfil", example = "1")
    private Long id;

    @Schema(description = "Información del usuario asociada al perfil", example = "Juan Pérez")
    private UsuarioDTO usuario;

    @Schema(description = "Lista de habilidades del usuario", example = "[\"Java\", \"SQL\", \"Spring Boot\"]")
    private List<String> habilidades;

    @Schema(description = "Descripción general del perfil del usuario", example = "Desarrollador con 5 años de experiencia en Java y SQL")
    private String descripcion;

    @Schema(description = "Lista de experiencias laborales del usuario", example = "[{\"empresa\": \"TechCorp\", \"puesto\": \"Desarrollador Backend\", \"duracion\": \"2 años\"}]")
    private List<Experiencia> experienciaLaboral;

    @Schema(description = "Lista de formaciones académicas del usuario", example = "[{\"institucion\": \"Universidad X\", \"titulo\": \"Ingeniero en Sistemas\", \"anioGraduacion\": \"2020\"}]")
    private List<FormacionAcademica> formacionAcademica;

    public PerfilDTO() {
    }

    public PerfilDTO(Long id, UsuarioDTO usuario, List<String> habilidades, String descripcion,
                     List<Experiencia> experienciaLaboral, List<FormacionAcademica> formacionAcademica) {
        this.id = id;
        this.usuario = usuario;
        this.habilidades = habilidades;
        this.descripcion = descripcion;
        this.experienciaLaboral = experienciaLaboral;
        this.formacionAcademica = formacionAcademica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
}
