package com.jobinsight.proyectoretojobinsight.Modell;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Schema(description = "Entidad que representa una oferta laboral publicada por una empresa en la plataforma JobInsight")
public class OfertaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la oferta laboral", example = "1")
    private Long id;

    @Schema(description = "Nombre de la empresa que ofrece la oferta", example = "Tech Solutions")
    private String nombreEmpresa;  // Nombre de la empresa que ofrece la oferta

    @Schema(description = "Título de la oferta laboral", example = "Desarrollador Java")
    private String titulo;  // Título de la oferta laboral

    @Schema(description = "Descripción de la oferta laboral", example = "Buscamos un desarrollador Java con experiencia en Spring Boot")
    private String descripcion;  // Descripción de la oferta

    @ElementCollection
    @Schema(description = "Lista de requisitos de la oferta laboral")
    private List<String> requisitos;  // Lista de requisitos de la oferta laboral

    @ElementCollection
    @Schema(description = "Lista de requisitos de experiencia de la oferta laboral")
    private List<String> requisitosExperiencia;  // Requisitos de experiencia laboral

    @Schema(description = "Ubicación del trabajo", example = "Madrid, España")
    private String ubicacion;  // Ubicación del trabajo

    @Schema(description = "Fecha de publicación de la oferta", example = "2025-06-25")
    private LocalDate fechaPublicacion;  // Fecha de publicación de la oferta

    @Schema(description = "Salario de la oferta", example = "2500.0")
    private Double salario;  // Salario ofrecido para la oferta laboral

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de trabajo (Remoto, Presencial, Mixto)", example = "PRESENCIAL")
    private TipoTrabajo tipoTrabajo;  // Enum: Remoto, Presencial, Mixto

    @OneToMany(mappedBy = "ofertaLaboral", cascade = CascadeType.ALL)
    @Schema(description = "Lista de coincidencias entre candidatos y la oferta laboral")
    private List<Coincidencia> coincidencias;  // Relación con la clase Coincidencia

    public OfertaLaboral() {

    }

    // Constructor sin id (para crear nuevas ofertas laborales)
    public OfertaLaboral(String nombreEmpresa, String titulo, String descripcion, List<String> requisitos, List<String> requisitosExperiencia, String ubicacion, LocalDate fechaPublicacion, Double salario, TipoTrabajo tipoTrabajo) {
        this.nombreEmpresa = nombreEmpresa;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.requisitosExperiencia = requisitosExperiencia;
        this.ubicacion = ubicacion;
        this.fechaPublicacion = fechaPublicacion;
        this.salario = salario;
        this.tipoTrabajo = tipoTrabajo;
    }

    // Constructor con id (para actualizar ofertas laborales existentes)
    public OfertaLaboral(Long id, String nombreEmpresa, String titulo, String descripcion, List<String> requisitos, List<String> requisitosExperiencia, String ubicacion, LocalDate fechaPublicacion, Double salario, TipoTrabajo tipoTrabajo) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.requisitosExperiencia = requisitosExperiencia;
        this.ubicacion = ubicacion;
        this.fechaPublicacion = fechaPublicacion;
        this.salario = salario;
        this.tipoTrabajo = tipoTrabajo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<String> requisitos) {
        this.requisitos = requisitos;
    }

    public List<String> getRequisitosExperiencia() {
        return requisitosExperiencia;
    }

    public void setRequisitosExperiencia(List<String> requisitosExperiencia) {
        this.requisitosExperiencia = requisitosExperiencia;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public List<Coincidencia> getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(List<Coincidencia> coincidencias) {
        this.coincidencias = coincidencias;
    }
}
