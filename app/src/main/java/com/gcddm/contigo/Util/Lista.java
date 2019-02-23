package com.gcddm.contigo.Util;

/**
 * Created by FC Bayern Munchen on 1/23/2017.
 */
public class Lista {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;

    public Lista(int idImagen, String textoEncima, String textoDebajo) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public String get_textoEncima() {
        return textoEncima;
    }

    public String get_textoDebajo() {
        return textoDebajo;
    }

    public int get_idImagen() {
        return idImagen;
    }
}
