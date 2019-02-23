package com.gcddm.contigo.db;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIRTHA on 15/05/2017.
 */
public class Juego extends SugarRecord {
    String nombrejuego;
    String estado;
    @Ignore
    List<PreguntaRespuestas> preguntaRespuestases;


    public Juego() {
        preguntaRespuestases = new ArrayList<>();
        this.nombrejuego = "";
        this.estado = "";
    }

    public Juego(String nombre_juego, String estado) {
        this.nombrejuego = nombre_juego;
        this.estado=estado;

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_juego() {
        return nombrejuego;
    }

    public void setNombre_juego(String nombre_juego) {
        this.nombrejuego = nombre_juego;
    }

    public List<PreguntaRespuestas> getPreguntas() {
        return PreguntaRespuestas.find(PreguntaRespuestas.class, "juego = ?",String.valueOf(getId()));
    }

    public void addPreguntas(PreguntaRespuestas preguntas) {
        this.preguntaRespuestases.add(preguntas);
    }

    public List<PreguntaRespuestas> getPreguntaRespuestases() {
        return preguntaRespuestases;
    }
}
