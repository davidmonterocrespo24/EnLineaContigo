package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by GRID-4 on 16/05/2017.
 */
public class Imagen extends SugarRecord {
    String path;
    Info info;

    public Imagen() {
    }

    public Imagen(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
