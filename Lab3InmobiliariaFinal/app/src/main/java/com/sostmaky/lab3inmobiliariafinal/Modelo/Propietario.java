package com.sostmaky.lab3inmobiliariafinal.Modelo;

import java.io.Serializable;

public class Propietario implements Serializable {

    private int id_propietario;
    private int dni ;
    private String apellido ;
    private String nombre ;
    private String direccion ;
    private String telefono ;
    private String email ;
    private String contrasena ;
    private String foto;
    private int estado_propietario ;


    public Propietario() {
    }

    public Propietario(int id_propietario, int dni, String apellido, String nombre, String direccion, String telefono, String email, String contrasena, String foto, int estado_propietario) {
        this.id_propietario = id_propietario;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena;
        this.foto = foto;
        this.estado_propietario = estado_propietario;
    }

    public Propietario(int dni, String apellido, String nombre, String direccion, String telefono, String email,String foto) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.foto = foto;
    }

    public int getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(int id_propietario) {
        this.id_propietario = id_propietario;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstado_propietario() {
        return estado_propietario;
    }

    public void setEstado_propietario(int estado_propietario) {
        this.estado_propietario = estado_propietario;
    }
}
