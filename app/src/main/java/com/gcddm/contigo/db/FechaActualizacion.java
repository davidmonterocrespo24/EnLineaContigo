package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by GRID-4 on 25/05/2017.
 */
public class FechaActualizacion extends SugarRecord {
    long fecha;

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public FechaActualizacion(long fecha) {
        this.fecha = fecha;
    }

    public FechaActualizacion() {
        this.fecha = 0;
    }
}
