package com.jobinsight.proyectoretojobinsight.DataTransferObject;

import java.util.List;

public class PefilDTO {
    private Long id;
    private UsuarioDTO usuario;
    private List<String> habilidades;
    private String descripcion;
    private List<Experiencia> experienciaLaboral;
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
