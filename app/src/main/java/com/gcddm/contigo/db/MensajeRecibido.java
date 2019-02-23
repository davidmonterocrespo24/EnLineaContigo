package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by Germany on 09-may-17.
 */
public class MensajeRecibido extends SugarRecord {
    long fecha;
    String texto;
    String remitente;
    boolean selected;

    public MensajeRecibido(long fecha, String texto, String remitente) {
        this.fecha = fecha;
        this.texto = texto;
        this.remitente = remitente;
        this.selected=false;
    }

    public MensajeRecibido() {
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
