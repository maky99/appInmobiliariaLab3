package com.sostmaky.lab3inmobiliariafinal.Modelo;

import java.io.Serializable;

public class TipoInmueble {

    private int id_Tipo_Inmueble;
    private String tipo;
    private int estado_Tipo_Inmueble;

    public TipoInmueble() {
    }

    @Override
    public String toString() {
        return tipo;
    }

    public TipoInmueble(int id_Tipo_Inmueble, String tipo, int estado_Tipo_Inmueble) {
        this.id_Tipo_Inmueble = id_Tipo_Inmueble;
        this.tipo = tipo;
        this.estado_Tipo_Inmueble = estado_Tipo_Inmueble;
    }

    public int getId_Tipo_Inmueble() {
        return id_Tipo_Inmueble;
    }

    public void setId_Tipo_Inmueble(int id_Tipo_Inmueble) {
        this.id_Tipo_Inmueble = id_Tipo_Inmueble;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEstado_Tipo_Inmueble() {
        return estado_Tipo_Inmueble;
    }

    public void setEstado_Tipo_Inmueble(int estado_Tipo_Inmueble) {
        this.estado_Tipo_Inmueble = estado_Tipo_Inmueble;
    }
}
