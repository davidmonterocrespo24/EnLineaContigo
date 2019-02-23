package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by MIRTHA on 15/05/2017.
 */
public class PreguntaRespuestas extends SugarRecord{
    Juego juego;

    String pregunta;
    String respuesta_correcta;
    String respuesta_incorrecta1;
    String respuesta_incorrecta2;

    public PreguntaRespuestas() {
    }

    public PreguntaRespuestas(String pregunta, String respuesta_correcta, String respuesta_incorrecta1, String respuesta_incorrecta2) {
        this.pregunta = pregunta;
        this.respuesta_correcta = respuesta_correcta;
        this.respuesta_incorrecta1 = respuesta_incorrecta1;
        this.respuesta_incorrecta2 = respuesta_incorrecta2;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta_correcta() {
        return respuesta_correcta;
    }

    public void setRespuesta_correcta(String respuesta_correcta) {
        this.respuesta_correcta = respuesta_correcta;
    }

    public String getRespuesta_incorrecta1() {
        return respuesta_incorrecta1;
    }

    public void setRespuesta_incorrecta1(String respuesta_incorrecta1) {
        this.respuesta_incorrecta1 = respuesta_incorrecta1;
    }

    public String getRespuesta_incorrecta2() {
        return respuesta_incorrecta2;
    }

    public void setRespuesta_incorrecta2(String respuesta_incorrecta2) {
        this.respuesta_incorrecta2 = respuesta_incorrecta2;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }
}
