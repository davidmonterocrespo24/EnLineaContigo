package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by GRID-4 on 26/05/2017.
 */
public class Programa extends SugarRecord {
    String nombre;
    String fecha;

    public Programa() {
    }

    public Programa(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
