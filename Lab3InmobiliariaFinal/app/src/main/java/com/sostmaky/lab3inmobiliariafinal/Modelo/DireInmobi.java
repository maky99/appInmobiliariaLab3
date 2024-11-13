package com.sostmaky.lab3inmobiliariafinal.Modelo;

import java.io.Serializable;

public class DireInmobi implements Serializable {

    private String nombre;
    private double latitud;
    private double longitud;

    public DireInmobi() {
    }

    public DireInmobi(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
