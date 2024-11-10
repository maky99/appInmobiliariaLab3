package com.sostmaky.lab3inmobiliariafinal.Modelo;

import java.io.Serializable;

public class Inmueble implements Serializable {

    private int id_Inmueble;
    private int id_Propietario;
    private String direccion;
    private String uso;
    private int ambientes;
    private Double tamano;
    private int id_Tipo_Inmueble;
    private String servicios;
    private int bano;
    private int cochera;
    private int patio;
    private double precio;
    private String condicion;
    private String foto;
    private int estado_Inmueble;

    public Inmueble(String direccion, String uso, int ambientes, Double tamano, int id_Tipo_Inmueble, String servicios, int bano, int cochera, int patio, double precio, String condicion, String foto, int estado_Inmueble) {
        this.id_Inmueble = id_Inmueble;
        this.id_Propietario = id_Propietario;
        this.direccion = direccion;
        this.uso = uso;
        this.ambientes = ambientes;
        this.tamano = tamano;
        this.id_Tipo_Inmueble = id_Tipo_Inmueble;
        this.servicios = servicios;
        this.bano = bano;
        this.cochera = cochera;
        this.patio = patio;
        this.precio = precio;
        this.condicion = condicion;
        this.foto = foto;
        this.estado_Inmueble = estado_Inmueble;
    }

    public int getId_Inmueble() {
        return id_Inmueble;
    }

    public void setId_Inmueble(int id_Inmueble) {
        this.id_Inmueble = id_Inmueble;
    }

    public int getId_Propietario() {
        return id_Propietario;
    }

    public void setId_Propietario(int id_Propietario) {
        this.id_Propietario = id_Propietario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }
    public Double getTamano() {
        return tamano;
    }

    public void setTamano(Double tamano) {
        this.tamano = tamano;
    }

    public int getId_Tipo_Inmueble() {
        return id_Tipo_Inmueble;
    }

    public void setId_Tipo_Inmueble(int id_Tipo_Inmueble) {
        this.id_Tipo_Inmueble = id_Tipo_Inmueble;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public int getBano() {
        return bano;
    }

    public void setBano(int bano) {
        this.bano = bano;
    }

    public int getCochera() {
        return cochera;
    }

    public void setCochera(int cochera) {
        this.cochera = cochera;
    }

    public int getPatio() {
        return patio;
    }

    public void setPatio(int patio) {
        this.patio = patio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstado_Inmueble() {
        return estado_Inmueble;
    }

    public void setEstado_Inmueble(int estado_Inmueble) {
        this.estado_Inmueble = estado_Inmueble;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id_Inmueble=" + id_Inmueble +
                ", id_Propietario=" + id_Propietario +
                ", direccion='" + direccion + '\'' +
                ", uso='" + uso + '\'' +
                ", ambientes=" + ambientes +
                ", tamano=" + tamano +
                ", id_Tipo_Inmueble=" + id_Tipo_Inmueble +
                ", servicios='" + servicios + '\'' +
                ", bano=" + bano +
                ", cochera=" + cochera +
                ", patio=" + patio +
                ", precio=" + precio +
                ", condicion='" + condicion + '\'' +
                ", foto='" + foto + '\'' +
                ", estado_Inmueble=" + estado_Inmueble +
                '}';
    }
}
