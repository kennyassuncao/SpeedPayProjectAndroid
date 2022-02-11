package com.example.speedpayprojet;

public class Transasao {
    private String idPagar;
    private String idReceber;
    private boolean isDone;
    public String valor;

    public Transasao() {
    }

    public Transasao(String idPagar, String idReceber, boolean isDone, String valor) {
        this.idPagar = idPagar;
        this.idReceber = idReceber;
        this.isDone = isDone;
        this.valor = valor;
    }

    public void setIdPagar(String idPagar) {
        this.idPagar = idPagar;
    }

    public void setIdReceber(String idReceber) {
        this.idReceber = idReceber;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIdPagar() {
        return idPagar;
    }

    public String getIdReceber() {
        return idReceber;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getValor() {
        return valor;
    }
}
