package com.sostmaky.lab3inmobiliariafinal.Modelo;

public class LoginCambio {
    private String contraVieja;
    private String contraNueva;

    public LoginCambio() {
    }

    public LoginCambio(String contraVieja,String contraNueva) {
        this.contraVieja = contraVieja;
        this.contraNueva = contraNueva;
    }

    public String getContraNueva() {
        return contraNueva;
    }

    public void setContraNueva(String contraNueva) {
        this.contraNueva = contraNueva;
    }

    public String getContraVieja() {
        return contraVieja;
    }

    public void setContraVieja(String contraVieja) {
        this.contraVieja = contraVieja;
    }
}
