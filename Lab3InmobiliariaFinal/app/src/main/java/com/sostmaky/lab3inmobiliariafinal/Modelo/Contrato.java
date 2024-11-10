package com.sostmaky.lab3inmobiliariafinal.Modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Contrato implements Serializable {

    private int id_Contrato;
    private int id_Inmueble;
    private int id_Propietario;
    private int id_Inquilino;
    private String fecha_Inicio;
    private int meses;
    private String fecha_Finalizacion;
    private double monto;
    private String  finalizacion_Anticipada;
    private int id_Creado_Pro;
    private int estado_Contrato;
    private Inmueble inmueble;
    private Propietario propietario;
    private Inquilino inquilino;


    public Contrato() {
    }

    public Contrato(int id_Contrato, int id_Inmueble, int id_Propietario, int id_Inquilino, String fecha_Inicio, int meses, String fecha_Finalizacion, double monto, String finalizacion_Anticipada, int id_Creado_Pro, int estado_Contrato, Inmueble inmueble, Propietario propietario, Inquilino inquilino) {
        this.id_Contrato = id_Contrato;
        this.id_Inmueble = id_Inmueble;
        this.id_Propietario = id_Propietario;
        this.id_Inquilino = id_Inquilino;
        this.fecha_Inicio = fecha_Inicio;
        this.meses = meses;
        this.fecha_Finalizacion = fecha_Finalizacion;
        this.monto = monto;
        this.finalizacion_Anticipada = finalizacion_Anticipada;
        this.id_Creado_Pro = id_Creado_Pro;
        this.estado_Contrato = estado_Contrato;
        this.inmueble = inmueble;
        this.propietario = propietario;
        this.inquilino = inquilino;
    }

    public int getId_Contrato() {
        return id_Contrato;
    }

    public void setId_Contrato(int id_Contrato) {
        this.id_Contrato = id_Contrato;
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

    public int getId_Inquilino() {
        return id_Inquilino;
    }

    public void setId_Inquilino(int id_Inquilino) {
        this.id_Inquilino = id_Inquilino;
    }

    public String getFecha_Inicio() {
        return fecha_Inicio;
    }

    public void setFecha_Inicio(String fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public String getFecha_Finalizacion() {
        return fecha_Finalizacion;
    }

    public void setFecha_Finalizacion(String fecha_Finalizacion) {
        this.fecha_Finalizacion = fecha_Finalizacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFinalizacion_Anticipada() {
        return finalizacion_Anticipada;
    }

    public void setFinalizacion_Anticipada(String finalizacion_Anticipada) {
        this.finalizacion_Anticipada = finalizacion_Anticipada;
    }

    public int getId_Creado_Pro() {
        return id_Creado_Pro;
    }

    public void setId_Creado_Pro(int id_Creado_Pro) {
        this.id_Creado_Pro = id_Creado_Pro;
    }

    public int getEstado_Contrato() {
        return estado_Contrato;
    }

    public void setEstado_Contrato(int estado_Contrato) {
        this.estado_Contrato = estado_Contrato;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id_Contrato=" + id_Contrato +
                ", id_Inmueble=" + id_Inmueble +
                ", id_Propietario=" + id_Propietario +
                ", id_Inquilino=" + id_Inquilino +
                ", fecha_Inicio='" + fecha_Inicio + '\'' +
                ", meses=" + meses +
                ", fecha_Finalizacion='" + fecha_Finalizacion + '\'' +
                ", monto=" + monto +
                ", finalizacion_Anticipada='" + finalizacion_Anticipada + '\'' +
                ", id_Creado_Pro=" + id_Creado_Pro +
                ", estado_Contrato=" + estado_Contrato +
                ", inmueble=" + inmueble +
                ", propietario=" + propietario +
                ", inquilino=" + inquilino +
                '}';
    }
}
