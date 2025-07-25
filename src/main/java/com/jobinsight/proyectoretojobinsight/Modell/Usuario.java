package com.jobinsight.proyectoretojobinsight.Modell;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
// import reactor.util.annotation.NonNull;
import org.springframework.lang.NonNull;


import java.time.LocalDate;

/**
 * Clase que representa a un usuario (candidato) en la plataforma JobInsight.
 * El usuario puede ser un candidato o un empleador, y tiene habilidades, experiencia y perfil asociados.
 */
@Schema(description = "Entidad que representa a un usuario de la plataforma JobInsight")
@Entity
@JsonIgnoreProperties({"perfil"})
public class Usuario {

    @Schema(description = "Identificador único del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan")
    @NonNull
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    @NonNull
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@correo.com")
    @NonNull
    private String email;

    @Schema(description = "Contraseña del usuario (cifrada)", example = "********")
    @NonNull
    private String password;

    @Schema(description = "Número de teléfono del usuario", example = "0987654321")
    private String telefono;

    @Schema(description = "Fecha de creación de la cuenta", example = "2025-06-25")
    private LocalDate fechaCreacion;

    @Schema(description = "Tipo de usuario (CANDIDATO o EMPLEADOR)", example = "CANDIDATO")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo; // Enum: CANDIDATO, EMPLEADOR. ADMINISTRADOR

    @Schema(description = "Dirección del usuario", example = "Quito, Ecuador")
    private String direccion;

    // El perfil se asocia al usuario, pero el perfil tiene el campo `usuario_id`
    @OneToOne(mappedBy = "usuario")
    @JsonBackReference
    private Perfil perfil;



    public Usuario() {
    }

    // Constructor para nuevos usuarios sin ID (para crear nuevos registros)
    public Usuario(@NonNull String nombre, @NonNull String apellido, @NonNull String email, @NonNull String password, String telefono, LocalDate fechaCreacion, TipoUsuario tipo, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.tipo = tipo;
        this.direccion = direccion;
    }

    // Constructor con ID (para actualizar o recuperar registros existentes)
    public Usuario(Long id, @NonNull String nombre, @NonNull String apellido, @NonNull String email, @NonNull String password, String telefono, LocalDate fechaCreacion, TipoUsuario tipo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.tipo = tipo;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String contraseña) {
        this.password = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
