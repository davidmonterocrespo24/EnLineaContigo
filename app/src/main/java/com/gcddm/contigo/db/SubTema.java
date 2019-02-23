package com.gcddm.contigo.db;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by David24 on 09/05/2017.
 */
public class SubTema  extends  SugarRecord{
    public String nombre;
    public TemaCaliente temaCaliente;
    public SubTema() {


    }

    public SubTema(String nombre,TemaCaliente temaCaliente) {
        this.nombre = nombre;
        this.temaCaliente = temaCaliente;
    }
 /*   public List<SubTema> getSubtemasByTemasCalientes(TemaCaliente temaCaliente ) {
        List<SubTema> books = SubTema.find(SubTema.class, "author = ?", new String{temaCaliente.getId()});
        return  books;
    }*/
}
