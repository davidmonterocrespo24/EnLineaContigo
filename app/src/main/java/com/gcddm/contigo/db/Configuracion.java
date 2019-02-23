package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by Germany on 09-may-17.
 */
public class Configuracion extends SugarRecord {
    /*boolean sms;
    boolean correo;
    boolean wifi;*/
    String service_web_address="http://192.168.43.231:1337/";

    public Configuracion(String service_web_address) {
        this.service_web_address = service_web_address;
    }

    public Configuracion() {
    }

    public String getService_web_address() {
        return service_web_address;
    }

    public void setService_web_address(String service_web_address) {
        this.service_web_address = service_web_address;
    }
}
