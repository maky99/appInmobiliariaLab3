package com.sostmaky.lab3inmobiliariafinal.Modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id_Contrato;
    private int id_Pago;
    private double importe;
    private int cuotaPaga;
    private String fecha;
    private double multa;
    private int estado_Pago;


    public Pago() {
    }

    public Pago(int id_Contrato, int id_Pago, double importe, int cuotaPaga, String fecha, double multa, int estado_Pago) {
        this.id_Contrato = id_Contrato;
        this.id_Pago = id_Pago;
        this.importe = importe;
        this.cuotaPaga = cuotaPaga;
        this.fecha = fecha;
        this.multa = multa;
        this.estado_Pago = estado_Pago;
    }

    public int getId_Contrato() {
        return id_Contrato;
    }

    public void setId_Contrato(int id_Contrato) {
        this.id_Contrato = id_Contrato;
    }

    public int getId_Pago() {
        return id_Pago;
    }

    public void setId_Pago(int id_Pago) {
        this.id_Pago = id_Pago;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getCuotaPaga() {
        return cuotaPaga;
    }

    public void setCuotaPaga(int cuotaPaga) {
        this.cuotaPaga = cuotaPaga;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public int getEstado_Pago() {
        return estado_Pago;
    }

    public void setEstado_Pago(int estado_Pago) {
        this.estado_Pago = estado_Pago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id_Contrato=" + id_Contrato +
                ", id_Pago=" + id_Pago +
                ", importe=" + importe +
                ", cuotaPaga=" + cuotaPaga +
                ", fecha='" + fecha + '\'' +
                ", multa=" + multa +
                ", estado_Pago=" + estado_Pago +
                '}';
    }
}
