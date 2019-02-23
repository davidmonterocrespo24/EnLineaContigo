package com.gcddm.contigo.db;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Germany on 08-may-17.
 */
public class Review extends SugarRecord {
    int tipo;
    String asunto;
    String mensaje;
    long fecha;
    Contacto contacto;
    public final static int ENVIADO=1;
    public final static int POR_ENVIAR=2;
    public final static int BORRADOR=3;
    int estado=0;
    int enviado=0;
    int datos = 0;
    String adjunto1;
    String adjunto2;
    String adjunto3;
    public final static int SMS=11;
    public final static int CORREO=21;
    public final static int WIFI=31;

    public final static int TODO=111;
    public final static int NOMBRE=211;
    public final static int ALIAS=311;


    boolean selected;


    public Review(){
    }

    public Review(Contacto contacto,int tipo, String asunto, String mensaje,long fecha, ArrayList<String> adjuntos) {
        this.tipo = tipo;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fecha=fecha;
        this.contacto=contacto;
        this.selected=false;
        this.adjunto1 = "";
        this.adjunto2 = "";
        this.adjunto3 = "";
        if(adjuntos != null){
            if(adjuntos.size() == 1){
                adjunto1 = adjuntos.get(0);
            }else if(adjuntos.size() == 2){
                adjunto1 = adjuntos.get(0);
                adjunto2 = adjuntos.get(1);
            }else if(adjuntos.size() == 3){
                adjunto1 = adjuntos.get(0);
                adjunto2 = adjuntos.get(1);
                adjunto3 = adjuntos.get(3);
            }
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleChecked() {
        selected = !selected ;
    }

    public String getAdjunto1() {
        return adjunto1;
    }

    public String getAdjunto2() {
        return adjunto2;
    }

    public String getAdjunto3() {
        return adjunto3;
    }

    public void setAdjuntos(ArrayList<String> adjuntos) {
        if(adjuntos != null){
            if(adjuntos.size() == 1){
                adjunto1 = adjuntos.get(0);
            }else if(adjuntos.size() == 2){
                adjunto1 = adjuntos.get(0);
                adjunto2 = adjuntos.get(1);
            }else if(adjuntos.size() == 3){
                adjunto1 = adjuntos.get(0);
                adjunto2 = adjuntos.get(1);
                adjunto3 = adjuntos.get(3);
            }
        }else{
            this.adjunto1 = "";
            this.adjunto2 = "";
            this.adjunto3 = "";
        }
    }
    public void setEnviadoPor(int enviado){
        this.enviado = enviado;
    }
    public int getEnviadoPor(){
        return this.enviado;
    }

    public int getDatos() {
        return datos;
    }

    public void setDatos(int datos) {
        this.datos = datos;
    }
}
