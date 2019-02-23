package com.gcddm.contigo.db;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by David24 on 09/05/2017.
 */
public class TemaCaliente  extends  SugarRecord{
   public String  nombre;
    public TemaCaliente() {
    }
    public TemaCaliente(String nombre) {
        this.nombre = nombre;
    }


}
