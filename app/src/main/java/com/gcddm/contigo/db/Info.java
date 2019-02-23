package com.gcddm.contigo.db;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIRTHA on 15/05/2017.
 */
public class Info extends SugarRecord {
    String nombreinfo;
    String textoinfo;
    String video;
    @Ignore
    List<Imagen> imagen;

    public Info() {

        this.imagen = new ArrayList<>();
        this.nombreinfo = "";
        this.textoinfo = "";
        this.video = "";
    }

    public Info(String nombre_info, String texto_info) {
        this.nombreinfo = nombre_info;
        this.textoinfo = texto_info;
        this.imagen = new ArrayList<>();

    }

    public String getNombre_info() {
        return nombreinfo;
    }

    public void setNombre_info(String nombre_info) {
        this.nombreinfo = nombre_info;
    }

    public String getTexto_info() {
        return textoinfo;
    }

    public void setTexto_info(String texto_info) {
        this.textoinfo = texto_info;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<Imagen> getImagen() {
        return imagen;
    }

    public void addImagen(Imagen img) {
        this.imagen.add(img);
    }

    public List<Imagen> getImagenes() {
        return Imagen.find(Imagen.class, "info = ?",String.valueOf(getId()));
    }
}
