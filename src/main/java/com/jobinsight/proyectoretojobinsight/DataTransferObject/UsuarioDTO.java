package com.jobinsight.proyectoretojobinsight.DataTransferObject;

import com.jobinsight.proyectoretojobinsight.Modell.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDTO {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "+593999999999")
    private String telefono;

    @Schema(description = "Dirección del usuario", example = "Quito, Ecuador")
    private String direccion;

    @Schema(description = "Tipo de usuario (ej. admin, regular)", example = "admin")
    private String tipo;

    @Schema(description = "Fecha de creación del usuario", example = "2025-07-18T10:00:00")
    private String fechaCreacion;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String apellido, String email,
                      String telefono, String direccion, TipoUsuario tipo, String fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipo = String.valueOf(tipo);
        this.fechaCreacion = fechaCreacion;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
