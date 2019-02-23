package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by Lizzy on 5/10/2017.
 */
public class Frases extends SugarRecord {
    String frase;
    String autor;


    public Frases(String frase, String autor) {
        this.frase = frase;
        this.autor = autor;
    }

    public Frases() {
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


}
