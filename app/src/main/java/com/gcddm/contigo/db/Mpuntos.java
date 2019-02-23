package com.gcddm.contigo.db;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by GRID-3 on 17/05/2017.
 */
public class Mpuntos extends SugarRecord {
    double lat;
    double lon;
    String nombre;
    String informacion;
    String direccion;
    String telef;
    String imagen0;
    String imagen1;
    String imagen2;
    String imagen3;

    public Mpuntos() {
        this.imagen0 = "";
        this.imagen1 = "";
        this.imagen2 = "";
        this.imagen3 = "";
    }

    public Mpuntos(double lat, double lon, String nombre, String informacion, String direccion, String telef, ArrayList<String> imagenes, String imagen) {
        this.lat = lat;
        this.lon = lon;
        this.nombre = nombre;
        this.informacion = informacion;
        this.direccion = direccion;
        this.telef = telef;
        this.imagen0 = "";
        this.imagen1 = "";
        this.imagen2 = "";
        this.imagen3 = "";
        if(imagenes != null){
            if(imagenes.size() == 1){
                imagen0 = imagenes.get(0);
            }else if(imagenes.size() == 2){
                imagen0 = imagenes.get(0);
                imagen1 = imagenes.get(1);
            }else if(imagenes.size() == 3){
                imagen0 = imagenes.get(0);
                imagen1 = imagenes.get(1);
                imagen2 = imagenes.get(2);
            }else if(imagenes.size() == 4){
                imagen0 = imagenes.get(0);
                imagen1 = imagenes.get(1);
                imagen2 = imagenes.get(2);
                imagen3 = imagenes.get(3);
            }
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }


    public ArrayList<String> getImagenes() {
        ArrayList<String> im = new ArrayList<>();
        if(!imagen0.matches("")){
            im.add(imagen0);
        }if(!imagen1.matches("")){
            im.add(imagen1);
        }if(!imagen2.matches("")){
            im.add(imagen2);
        }if(!imagen3.matches("")){
            im.add(imagen3);
        }

        return im;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        if(imagenes != null){
            if(imagenes.size() == 1){
                imagen0 = imagenes.get(0);
            }else if(imagenes.size() == 2){
                imagen0 = imagenes.get(0);
                imagen1 = imagenes.get(1);
            }else if(imagenes.size() == 3){
                imagen0 = imagenes.get(0);
                imagen1 = imagenes.get(1);
                imagen3 = imagenes.get(2);
            }else if(imagenes.size() == 4){
                imagen0 = imagenes.get(0);
                imagen1 = imagenes.get(1);
                imagen2 = imagenes.get(2);
                imagen3 = imagenes.get(3);
            }
        }else{
            this.imagen0 = "";
            this.imagen1 = "";
            this.imagen2 = "";
            this.imagen3 = "";
        }
    }
}
