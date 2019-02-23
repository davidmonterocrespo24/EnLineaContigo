package com.gcddm.contigo;

/**
 * Created by Germany on 18-may-17.
 */
public class Servicio {
    int fondo;
    int imagen;
    String nombre;

    public Servicio(int fondo,int imagen, String nombre) {
        this.fondo = fondo;
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public Servicio() {
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFondo() {
        return fondo;
    }

    public void setFondo(int fondo) {
        this.fondo = fondo;
    }
}
